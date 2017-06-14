package com.zeroarst.viewtransitioner;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pools;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;


public abstract class ViewTransitioner<T extends View> extends FrameLayout {

    private final static String TAG = "ViewTransitionLayout";

    private final static int DEFAULT_POOL_SIZE = 5;
    private Pools.Pool<T> mPools;

    public ViewTransitioner(@NonNull Context context, int poolSize) {
        super(context);
        init(poolSize);
    }

    public ViewTransitioner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(int poolSize) {
        setPoolSize(poolSize);
    }

    public void setPoolSize(int poolSize) {
        this.mPools = new Pools.SimplePool<>(poolSize == -1 ? DEFAULT_POOL_SIZE : poolSize);
    }

    public abstract T createView();

    private T mShowingView;

    private Pools.Pool<T> getPool() {
        if (mPools == null)
            setPoolSize(-1);
        return mPools;
    }

    private class AnimationHolder {
        AnimationHolder(ObjectAnimator inAnim, ObjectAnimator outAnim) {
            this.inAnim = inAnim;
            this.outAnim = outAnim;
        }

        ObjectAnimator inAnim;
        ObjectAnimator outAnim;
    }

    interface OnViewAcquiringListener<T> {
        void onAcquired(T view);
    }

    public void transition(final ObjectAnimator inAnim, ObjectAnimator outAnim, OnViewAcquiringListener<T> listener) {

        T vw = getPool().acquire();

        if (vw == null)
            vw = createView();

        vw.setTag(new AnimationHolder(inAnim, outAnim));

        if (mShowingView != null) {
            AnimationHolder ah = (AnimationHolder) vw.getTag();

            final T finalVw = mShowingView;
            // Cancel current running animation of showing view.
            ah.inAnim.cancel();

            // Execute out animation on showing view.
            ah.outAnim.setTarget(finalVw);
            ah.outAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    removeView(finalVw);
                    getPool().release(finalVw);
                }
            });
            ah.outAnim.start();
        }

        mShowingView = vw;

        addView(vw);

        // Callback for caller to process things on the view.
        listener.onAcquired(vw);

        inAnim.setTarget(vw);
        inAnim.start();
    }

}
