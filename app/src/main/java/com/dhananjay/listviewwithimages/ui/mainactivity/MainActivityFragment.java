package com.dhananjay.listviewwithimages.ui.mainactivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dhananjay.listviewwithimages.R;
import com.dhananjay.listviewwithimages.adapters.RowListAdapter;
import com.dhananjay.listviewwithimages.api.ApiResponse;
import com.dhananjay.listviewwithimages.models.Response;
import com.dhananjay.listviewwithimages.models.Row;
import com.dhananjay.listviewwithimages.repository.ViewModelFactory;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityFragment extends Fragment {

    @BindView(R.id.btnRefresh)
    ImageButton btnRefresh;

    @BindView(R.id.lstView)
    ListView lstView;

    @Inject
    ViewModelFactory viewModelFactory;

    private MainActivityViewModel mViewModel;

    private ProgressDialog progressDialog;

    private ArrayList<Row> rowList;
    private RowListAdapter rowListAdapter;

    public static MainActivityFragment newInstance() {
        return new MainActivityFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.main_activity_fragment, container, false);
        ButterKnife.bind(this, fragmentView);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Processing...\nPlease wait...");

        rowList = new ArrayList<>();
        rowListAdapter = new RowListAdapter(getActivity(), rowList);

        setRetainInstance(true);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);
        // TODO: Use the ViewModel

        mViewModel.response().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                if (apiResponse != null) {
                    consumeResponse(apiResponse);
                }
            }
        });

        lstView.setAdapter(rowListAdapter);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData(getActivity());
            }
        });

        loadData(getActivity());
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void loadData(Context context) {
        if (!isNetworkAvailable(context)) {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        } else {
            mViewModel.getResponse();
        }
    }

    /*
     * method to handle response
     * */
    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                progressDialog.show();
                break;

            case SUCCESS:
                progressDialog.dismiss();
                if (apiResponse.data != null) {
                    renderSuccessResponse(apiResponse.data);
                }
                break;

            case ERROR:
                progressDialog.dismiss();
                Toast.makeText(getActivity(), getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    /*
     * method to handle success response
     * */
    private void renderSuccessResponse(Response response) {
        if (response != null) {
            if (response.getTitle() != null) {
                Log.d("response=", response.getTitle());
                if (getActivity() != null) {
                    getActivity().setTitle(response.getTitle());
                }
            }
            if (response.getRows() != null) {
                rowList = response.getRows();
                rowListAdapter.updateList(rowList);
            }
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
        }
    }

}
