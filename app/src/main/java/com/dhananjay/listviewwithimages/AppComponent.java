package com.dhananjay.listviewwithimages;

import com.dhananjay.listviewwithimages.ui.mainactivity.MainActivityFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {

    void doInjection(MainActivityFragment mainActivityFragment);

}
