package com.match.maker.featureModules.landing.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.match.maker.R;
import com.match.maker.application.MatchMakerApplication;
import com.match.maker.databinding.ActivityHomeBinding;
import com.match.maker.db.tables.MatchingUsersTable;
import com.match.maker.featureModules.landing.adapters.HomeActivityRecyclerAdapter;
import com.match.maker.featureModules.landing.di.DaggerHomeActivityComponent;
import com.match.maker.featureModules.landing.di.HomeActivityComponent;
import com.match.maker.featureModules.login.LoginActivity;
import com.match.maker.preferences.CommonPreferences;
import com.match.maker.utils.Util;

import javax.inject.Inject;

/**
 * Created by ravindra on 16,January,2019
 */
public class HomeActivity extends AppCompatActivity {


    @Inject
    Util util;

    @Inject
    CommonPreferences prefs;

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
        binding.toolbar.setTitleTextAppearance(this, R.style.WhiteToolBarTitleMedium);
        setSupportActionBar(binding.toolbar);

        prefs.setFirstTimeLogin(false);

        util.showLoadingDialog(this);

        setAdapter();
        setObservers();
        setClickListeners();


        viewModel.getAllMatches(50);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.more:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logOut() {

        prefs.setFirstTimeLogin(true);

        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        startActivity(intent);
        finish();

    }

    private void setClickListeners() {

        binding.pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getAllMatches(50);
            }
        });

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
                binding.pullToRefresh.setRefreshing(false);
            }
        });

    }

    private void allMatchesDataSuccess() {

        viewModel.getAllMatchesData().observe(this, new Observer<MatchingUsersTable>() {
            @Override
            public void onChanged(@Nullable MatchingUsersTable matchingUsersResponse) {
                binding.txtNoData.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.pullToRefresh.setRefreshing(false);
                util.dismissLoadingDialog();
                updateData(matchingUsersResponse);
            }
        });

    }

    private void updateData(MatchingUsersTable matchingUsersResponse) {
        homeActivityRecyclerAdapter.updateData(matchingUsersResponse.getResults());
    }

    private void setAdapter() {

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        homeActivityRecyclerAdapter = new HomeActivityRecyclerAdapter(this);
        homeActivityComponent.inject(homeActivityRecyclerAdapter);
        binding.recyclerView.setAdapter(homeActivityRecyclerAdapter);


    }
}
