package com.example.core.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

public class BaseAnimation implements Animator.AnimatorListener{
    private ObjectAnimator animator;

    @Override
    public void onAnimationStart(Animator animation) {
        if(animatorListener == null) return;
        animatorListener.AnimationStart(animation);
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if(animatorListener == null) return;
        animatorListener.onAnimationEnd(animation);
        animatorListener = null;
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        if(animatorListener == null) return;
        animatorListener.onAnimationCancel(animation);
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        if(animatorListener == null) return;
        animatorListener.onAnimationRepeat(animation);
    }

    public interface AnimatorListener{
        void AnimationStart(Animator animator);
        void onAnimationEnd(Animator animation);
        void onAnimationCancel(Animator animation);
        void onAnimationRepeat(Animator animation);
    }
    private AnimatorListener animatorListener;
    public void setAnimatorListener(AnimatorListener animatorListener) {
        this.animatorListener = animatorListener;
    }
    public void removeAnimatorListener() {
        animatorListener = null;
    }
    /**
     * scaleX缩放动画
     * @param view
     */
    public void scaleXAnimation(View view,float startValues,float endValues,long duration) {
        animator = ObjectAnimator.ofFloat(view,"scaleX",startValues,endValues);
        animator.setDuration(duration);
        animator.start();
        animator.addListener(this);
    }
    public void scaleAnimation(View view,float startValues,float endValues,long duration) {
        PropertyValuesHolder animator1 = PropertyValuesHolder.ofFloat("scaleX",
                startValues, endValues);
        //Y轴缩放动画
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                startValues, endValues);
        animator = ObjectAnimator.ofPropertyValuesHolder(view,
                animator1, animator2);
        animator.setDuration(duration);
        animator.start();
        animator.addListener(this);
    }
    /**
     * 等比缩放动画
     * @param view
     */
    public void scaleXYAnimation(View view,float startValues,float endValues,long duration) {
        PropertyValuesHolder animator1 = PropertyValuesHolder.ofFloat("scaleX",
                startValues, endValues);
        //Y轴缩放动画
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                startValues, endValues);
        animator = ObjectAnimator.ofPropertyValuesHolder(view,
                animator1, animator2);
        animator.setDuration(duration);
        animator.setInterpolator(new AnimationInterpolator.JellyInterpolator());
        animator.start();
        animator.addListener(this);
    }

    /**
     * 向上平移淡出
     */
    public void alphaTran(View view,long duration) {
        PropertyValuesHolder animator1 = PropertyValuesHolder.ofFloat("translationY",50,0);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("alpha",0,1f);
        animator = ObjectAnimator.ofPropertyValuesHolder(view,animator1,animator2);
        animator.setDuration(duration);
        animator.start();
    }

    /**
     * 从右向左平移
     */
    public void tran(View view) {
        animator = ObjectAnimator.ofFloat(view, "translationX", 1000, 0);
        animator.setDuration(600);
        animator.setInterpolator(new AnimationInterpolator.DampInterpolator());
        animator.start();

    }
}
