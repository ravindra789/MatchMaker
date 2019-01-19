package com.match.maker.featureModules.landing.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.match.maker.R;
import com.match.maker.application.MatchMakerApplication;
import com.match.maker.databinding.ActivityHomeBinding;
import com.match.maker.featureModules.landing.adapters.HomeActivityRecyclerAdapter;
import com.match.maker.featureModules.landing.di.DaggerHomeActivityComponent;
import com.match.maker.featureModules.landing.di.HomeActivityComponent;
import com.match.maker.featureModules.landing.models.MatchingUsersResponse;
import com.match.maker.utils.Util;

import javax.inject.Inject;

/**
 * Created by ravindra on 16,January,2019
 */
public class HomeActivity extends AppCompatActivity {


    @Inject
    Util util;

    private ActivityHomeBinding binding;
    private HomeActivityRecyclerAdapter homeActivityRecyclerAdapter;
    private HomeActivityComponent homeActivityComponent;
    private HomeActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        viewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);

        homeActivityComponent = DaggerHomeActivityComponent.builder()
                .applicationComponent(((MatchMakerApplication) getApplication()).getApplicationComponent())
                .build();
        homeActivityComponent.inject(this);
        homeActivityComponent.inject(viewModel);

        binding.toolbar.setTitle("Home");
        binding.toolbar.setTitleTextAppearance(this,  R.style.WhiteToolBarTitleMedium);


        util.showLoadingDialog(this);


        setAdapter();
        setObservers();


        viewModel.getAllMatches(50);



    }

    private void setObservers() {

        allMatchesDataSuccess();
        allMatchesDataError();

    }

    private void allMatchesDataError() {

        viewModel.getAllMatchesDataError().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                Toast.makeText(HomeActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                binding.txtNoData.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
                util.dismissLoadingDialog();
            }
        });

    }

    private void allMatchesDataSuccess() {

       viewModel.getAllMatchesData().observe(this, new Observer<MatchingUsersResponse>() {
           @Override
           public void onChanged(@Nullable MatchingUsersResponse matchingUsersResponse) {
               binding.txtNoData.setVisibility(View.GONE);
               binding.recyclerView.setVisibility(View.VISIBLE);
               util.dismissLoadingDialog();
               updateData(matchingUsersResponse);
           }
       });

    }

    private void updateData(MatchingUsersResponse matchingUsersResponse) {
        homeActivityRecyclerAdapter.updateData(matchingUsersResponse.getResults());
    }

    private void setAdapter() {

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        homeActivityRecyclerAdapter = new HomeActivityRecyclerAdapter(this);
        homeActivityComponent.inject(homeActivityRecyclerAdapter);
        binding.recyclerView.setAdapter(homeActivityRecyclerAdapter);


    }
}
