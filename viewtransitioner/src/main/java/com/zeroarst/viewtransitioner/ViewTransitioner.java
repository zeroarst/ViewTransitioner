package com.zeroarst.viewtransitioner;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.util.Pools;
import android.view.View;
import android.view.ViewGroup;


public class ViewTransitioner<T extends View> {

    private final static String TAG = "ViewTransitioner";

    private final static int DEFAULT_POOL_SIZE = 5;
    private Pools.Pool<T> mPools;

    private ViewGroup mContainer;

    public ViewTransitioner(ViewGroup container) {
        this(container, DEFAULT_POOL_SIZE);
    }

    public ViewTransitioner(ViewGroup container, int poolSize) {
        mContainer = container;
        setPoolSize(poolSize);
    }

    private void setPoolSize(int poolSize) {
        this.mPools = new Pools.SimplePool<>(poolSize);
    }

    private T mShowingView;

    private Pools.Pool<T> getPool() {
        if (mPools == null)
            setPoolSize(-1);
        return mPools;
    }

    private class AnimationHolder {
        AnimationHolder(Animator inAnim, Animator outAnim) {
            this.inAnim = inAnim;
            this.outAnim = outAnim;
        }

        Animator inAnim;
        Animator outAnim;
    }

    public interface OnTransitionListener<T> {

        /**
         * Called when unable to acquire a {@link View} from a pool.
         *
         * @return
         */
        T onCreateView();

        /**
         * Called when a {@link View} acquired from either pool or new creation.
         *
         * @param view
         * @param fromPool True if from pool. False if from new creation.
         */
        void onAcquired(T view, boolean fromPool);

        /**
         * Called when out {@link Animator} has ended. At this point this {@link View} has been release to pool and removed from the view tree.
         *
         * @param view
         */
        void onAnimationEnded(T view);

    }

    public void transition(final Animator inAnim, Animator outAnim, final OnTransitionListener<T> listener) {

        boolean fromPool = true;
        T vw = getPool().acquire();

        if (vw == null) {
            fromPool = false;
            vw = listener.onCreateView();
        }

        if (vw == null)
            return;

        // Callback for caller to process things on the view.
        listener.onAcquired(vw, fromPool);

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
                    mContainer.removeView(finalVw);
                    getPool().release(finalVw);
                    listener.onAnimationEnded(finalVw);
                }
            });
            ah.outAnim.start();
        }

        mShowingView = vw;

        mContainer.addView(vw, 0);

        inAnim.setTarget(vw);
        inAnim.start();
    }

}
