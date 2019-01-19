package com.match.maker.featureModules.splash.di;

import com.match.maker.di.component.ApplicationComponent;
import com.match.maker.di.scopes.UserScope;
import com.match.maker.featureModules.splash.views.SplashActivity;

import dagger.Component;

/**
 * Created by ravindra on 16,January,2019
 */
@UserScope
@Component(dependencies = ApplicationComponent.class, modules = SplashActivityModule.class)
public interface SplashActivityComponent {
    void inject(SplashActivity splashActivity);
}
