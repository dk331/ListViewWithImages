package com.dhananjay.listviewwithimages.ui.mainactivity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dhananjay.listviewwithimages.api.ApiResponse;
import com.dhananjay.listviewwithimages.models.Response;
import com.dhananjay.listviewwithimages.repository.Repository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivityViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
    private Repository repository;

    public MainActivityViewModel() {
    }

    public MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ApiResponse> response() {
        return responseLiveData;
    }

    /*
     * method to call normal getResponse api
     * */
    public void getResponse() {

        disposables.add(repository.getResponse()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable d) throws Exception {
                        responseLiveData.setValue(ApiResponse.loading());
                    }
                })
                .subscribe(
                        new Consumer<Response>() {
                            @Override
                            public void accept(Response result) throws Exception {
                                responseLiveData.setValue(ApiResponse.success(result));
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                responseLiveData.setValue(ApiResponse.error(throwable));
                            }
                        }
                ));
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}
