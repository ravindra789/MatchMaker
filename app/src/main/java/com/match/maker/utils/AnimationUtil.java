package com.match.maker.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.match.maker.R;

/**
 * Created by ravindra on 16,January,2019
 */
public class AnimationUtil {

    private static AnimationUtil instance;
    private Context mContext;


    public static AnimationUtil getInstance(){
        if(instance == null ){
            synchronized (AnimationUtil.class) {
                if (instance == null) {
                    instance = new AnimationUtil();
                }
            }
        }

        return instance;
    }

    public void setContext(Context context){
        mContext = context;
    }

    public void slideOutToLeft(View view) {
        runSimpleAnimation( view, R.anim.slide_to_left);
    }

    public void slideOutToRight(View view) {
        runSimpleAnimation(view, R.anim.slide_to_right);
    }

    public void animateSlideRight(View view){
        loadAnimation(view, R.anim.slide_to_right);
    }

    public void animateSlideLeft(View view){
        loadAnimation(view, R.anim.slide_to_left);
    }

    private void runSimpleAnimation(View view, int animationId) {
        view.startAnimation(AnimationUtils.loadAnimation(
                mContext, animationId
        ));
    }

    private void loadAnimation(View view, int animationId) {
        Animation animation = AnimationUtils.loadAnimation(mContext, animationId);
        view.startAnimation(animation);
    }

    public int returnSlideToRightAnimation(){
        return R.anim.slide_to_right;
    }

    public int returnSlideToLeftAnimation(){
        return R.anim.slide_to_left;
    }



}