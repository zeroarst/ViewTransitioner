package com.zeroarst.viewtransitionerexample;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zeroarst.viewtransitioner.ImageViewTransitioner;
import com.zeroarst.viewtransitioner.ViewTransitioner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageViewTransitioner mIvTransitionLo;

    static List<Integer> mIconResources;

    Iterator<Integer> mIter;

    static {
        mIconResources = new ArrayList<>();
        mIconResources.add(R.drawable.w1);
        mIconResources.add(R.drawable.w2);
        mIconResources.add(R.drawable.w3);
        mIconResources.add(R.drawable.w4);
        mIconResources.add(R.drawable.w5);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIvTransitionLo = (ImageViewTransitioner) findViewById(R.id.is);
        mIvTransitionLo.setPoolSize(10);
        mIvTransitionLo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.is:
                showNextImage();
                break;
        }
    }

    void showNextImage() {
        if (mIter == null || !mIter.hasNext())
            mIter = mIconResources.iterator();

        RequestBuilder<Drawable> rb = Glide.with(this).load(mIter.next());

        rb.into(new ViewTarget<ViewTransitioner, Drawable>(mIvTransitionLo) {
            @Override
            public synchronized void onResourceReady(final Drawable resource, Transition<? super Drawable> transition) {

                // In anim
                final ObjectAnimator inRotateAnim = new ObjectAnimator();
                inRotateAnim.setProperty(View.ROTATION_Y);
                inRotateAnim.setFloatValues(0);
                inRotateAnim.setDuration(1000);

                final ObjectAnimator inSlideAnim = new ObjectAnimator();
                inSlideAnim.setProperty(View.TRANSLATION_X);
                inSlideAnim.setFloatValues(0);
                inSlideAnim.setDuration(1000);

                final ObjectAnimator inAlphaAnim = new ObjectAnimator();
                inAlphaAnim.setProperty(View.ALPHA);
                inAlphaAnim.setFloatValues(1f);
                inAlphaAnim.setDuration(1000);

                final AnimatorSet asIn = new AnimatorSet();
                asIn.playTogether(inSlideAnim, inAlphaAnim, inRotateAnim);

                // Out anim
                final ObjectAnimator outRotateAnim = new ObjectAnimator();
                outRotateAnim.setProperty(View.ROTATION_Y);
                outRotateAnim.setFloatValues(90);
                outRotateAnim.setDuration(1000);

                final ObjectAnimator outSlideAnim = new ObjectAnimator();
                outSlideAnim.setProperty(View.TRANSLATION_X);
                outSlideAnim.setFloatValues(-mIvTransitionLo.getWidth());
                outSlideAnim.setDuration(1000);

                final ObjectAnimator outAlphaAnim = new ObjectAnimator();
                outAlphaAnim.setProperty(View.ALPHA);
                outAlphaAnim.setFloatValues(0f);
                outAlphaAnim.setDuration(1000);

                final AnimatorSet asOut = new AnimatorSet();
                asOut.playTogether(outSlideAnim, outAlphaAnim, outRotateAnim);

                mIvTransitionLo.transition(asIn, asOut, new ViewTransitioner.OnViewAcquiringListener<ImageView>() {
                    @Override
                    public void onAcquired(ImageView view) {
                        // Init anim
                        view.setTranslationX(mIvTransitionLo.getWidth());
                        view.setAlpha(0f);
                        view.setRotationY(-90);
                        view.setImageDrawable(resource);
                    }
                });

            }
        });

    }

}
