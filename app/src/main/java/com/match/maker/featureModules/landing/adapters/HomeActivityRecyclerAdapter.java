package com.match.maker.featureModules.landing.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.match.maker.R;
import com.match.maker.databinding.RecyclerAdapterHomeActivityBinding;
import com.match.maker.featureModules.landing.models.Location;
import com.match.maker.featureModules.landing.models.Name;
import com.match.maker.featureModules.landing.models.Result;
import com.match.maker.utils.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ravindra on 17,January,2019
 */
public class HomeActivityRecyclerAdapter extends RecyclerView.Adapter<HomeActivityRecyclerAdapter.MyViewHolder> {

    @Inject
    AnimationUtil animationUtil;

    private List<Result> results = new ArrayList<>();
    private Activity activity;

    public HomeActivityRecyclerAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public HomeActivityRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        RecyclerAdapterHomeActivityBinding binding = RecyclerAdapterHomeActivityBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeActivityRecyclerAdapter.MyViewHolder holder, final int position) {

        Result data = results.get(position);

        if (data != null) {

            holder.binding.txtCardTime.setText(setTimeStatus(data.getRegistered().getAge()));
            holder.binding.txtUserName.setText(formatUserName(data.getName()));
            holder.binding.txtUserPersonalDescription.setText(formatPersonalDescription(data));
            holder.binding.txtUserProfessionalDescription.setText(formatProfessionalDetails(data.getLocation()));


            RequestOptions requestOptions = new RequestOptions();
            requestOptions.fitCenter().placeholder(R.drawable.placeholder);
            Glide.with(activity)
                    .setDefaultRequestOptions(requestOptions)
                    .load(data.getPicture().getMedium())
                    .into(holder.binding.imgProfile);


        }


    }

    private String formatProfessionalDetails(Location location) {

        String details;

        details = "" + location.getCity() + ", " + location.getState();

        return details;

    }

    private String formatPersonalDescription(Result data) {

        String description;

        String gender;
        if (data.getGender().equalsIgnoreCase("male")) {
            gender = "Male";
        } else {
            gender = "Female";
        }
        description = "" + gender + ", " + data.getDob().getAge() + " Years";


        return description;
    }

    private String formatUserName(Name name) {

        String formattedName;

        String tempTitle = name.getTitle();
        String title = tempTitle.substring(0, 1).toUpperCase() + tempTitle.substring(1);

        String tempFirstName = name.getFirst();
        String FirstName = tempFirstName.substring(0, 1).toUpperCase() + tempFirstName.substring(1);

        String tempLastName = name.getLast();
        String LastName = tempLastName.substring(0, 1).toUpperCase() + tempLastName.substring(1);

        formattedName = title + " " + FirstName + " " + LastName;


        return formattedName;
    }

    private String setTimeStatus(Integer age) {

        String status;

        if (age == 0) {
            status = "Moments Ago.";
        } else if (age < 5) {
            status = "Few Hours Ago.";
        } else {
            status = "" + age + " Hours Ago.";
        }

        return status;
    }

    public void updateData(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (results != null && results.size() > 0) {
            return results.size();
        }
        return 0;
    }

    void deleteItem(int index, CardView cardView, int calledFrom) {

        if (calledFrom == 0) {
            animationUtil.animateSlideRight(cardView);
        } else {
            animationUtil.animateSlideLeft(cardView);
        }

        results.remove(index);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index, results.size());
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerAdapterHomeActivityBinding binding;

        public MyViewHolder(RecyclerAdapterHomeActivityBinding recyclerAdapterHomeActivityBinding) {
            super(recyclerAdapterHomeActivityBinding.getRoot());
            binding = recyclerAdapterHomeActivityBinding;


            binding.btnConnect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(getAdapterPosition(), binding.cardView, 0);
                }
            });

            binding.btnDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(getAdapterPosition(), binding.cardView, 1);
                }
            });

        }
    }

}
