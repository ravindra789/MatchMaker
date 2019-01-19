package com.match.maker.featureModules.landing.di;

import com.match.maker.di.component.ApplicationComponent;
import com.match.maker.di.scopes.UserScope;
import com.match.maker.featureModules.landing.adapters.HomeActivityRecyclerAdapter;
import com.match.maker.featureModules.landing.views.HomeActivity;
import com.match.maker.featureModules.landing.views.HomeActivityViewModel;

import dagger.Component;

/**
 * Created by ravindra on 17,January,2019
 */
@UserScope
@Component(dependencies = ApplicationComponent.class, modules = HomeActivityModule.class)
public interface HomeActivityComponent {

    void inject(HomeActivity homeActivity);
    void inject(HomeActivityViewModel homeActivityViewModel);
    void inject(HomeActivityRecyclerAdapter homeActivityRecyclerAdapter);

}
