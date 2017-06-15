package com.zeroarst.viewtransitionerexample;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zeroarst.viewtransitioner.ViewTransitioner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private static final String ARG_EX_ID = "ARG_EX_ID";
    private ViewTransitioner<ImageView> mVwTransitioner;

    private Iterator<Integer> mIter;

    private int mExampleId;
    private static final int EX_1 = 1;
    private static final int EX_2 = 2;
    private static final int EX_3 = 3;
    private static final int EX_4 = 4;
    private static final int EX_5 = 5;

    private static List<Integer> ICON_RESOURCES;

    static {
        ICON_RESOURCES = new ArrayList<>();
        ICON_RESOURCES.add(R.drawable.w1);
        ICON_RESOURCES.add(R.drawable.w2);
        ICON_RESOURCES.add(R.drawable.w3);
        ICON_RESOURCES.add(R.drawable.w4);
        ICON_RESOURCES.add(R.drawable.w5);
        ICON_RESOURCES.add(R.drawable.w6);
        ICON_RESOURCES.add(R.drawable.w7);
        ICON_RESOURCES.add(R.drawable.w8);
        ICON_RESOURCES.add(R.drawable.w9);
    }

    ViewGroup mContainerLo;

    public static MainFragment create(int exId) {
        MainFragment f = new MainFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        args.putInt(ARG_EX_ID, exId);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mExampleId = getArguments().getInt(ARG_EX_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Note that clipChildren has to be set to false otherwise the view won't be drawn outside its parent's bounds.
        final View vw = inflater.inflate(R.layout.fragm_main, container, false);
        mContainerLo = (ViewGroup) vw.findViewById(R.id.lo_container);
        mContainerLo.setOnClickListener(this);
        mVwTransitioner = new ViewTransitioner<>(mContainerLo);
        return vw;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lo_container:
                showNextImage();
                break;
        }
    }

    abstract class ImageViewTransitionListener implements ViewTransitioner.OnTransitionListener<ImageView> {

        @Override
        public ImageView onCreateView() {
            return new ImageView(getContext());
        }

        @Override
        public void onAnimationEnded(ImageView view) {
            // Do nothing.
        }
    }

    private void showNextImage() {
        if (mIter == null || !mIter.hasNext())
            mIter = ICON_RESOURCES.iterator();

        RequestBuilder<Drawable> rb = Glide.with(this).load(mIter.next());

        switch (mExampleId) {
            case EX_1:
                rb.into(new ViewTarget<View, Drawable>(mContainerLo) {
                    @Override
                    public synchronized void onResourceReady(final Drawable resource, Transition<? super Drawable> transition) {

                        // In anim
                        final ObjectAnimator inAlphaAnim = new ObjectAnimator();
                        inAlphaAnim.setProperty(View.ALPHA);
                        inAlphaAnim.setFloatValues(1f);
                        inAlphaAnim.setDuration(1000);

                        // Out anim
                        final ObjectAnimator outAlphaAnim = new ObjectAnimator();
                        outAlphaAnim.setProperty(View.ALPHA);
                        outAlphaAnim.setFloatValues(0f);
                        outAlphaAnim.setDuration(1000);

                        mVwTransitioner.transition(inAlphaAnim, outAlphaAnim, new ImageViewTransitionListener() {
                            @Override
                            public void onAcquired(ImageView view, boolean fromPool) {
                                // Init
                                view.setAlpha(0f);
                                view.setImageDrawable(resource);
                            }
                        });

                    }
                });
                break;
            case EX_2:
                rb.into(new ViewTarget<View, Drawable>(mContainerLo) {
                    @Override
                    public synchronized void onResourceReady(final Drawable resource, Transition<? super Drawable> transition) {

                        // In anim
                        final ObjectAnimator inAlphaAnim = new ObjectAnimator();
                        inAlphaAnim.setProperty(View.ALPHA);
                        inAlphaAnim.setFloatValues(1f);
                        inAlphaAnim.setDuration(500);

                        final ObjectAnimator inRotateAnim = new ObjectAnimator();
                        inRotateAnim.setProperty(View.ROTATION_Y);
                        inRotateAnim.setFloatValues(0);
                        inRotateAnim.setDuration(500);

                        final AnimatorSet asIn = new AnimatorSet();
                        asIn.playTogether(inAlphaAnim, inRotateAnim);

                        // Out anim
                        final ObjectAnimator outRotateAnim = new ObjectAnimator();
                        outRotateAnim.setProperty(View.ROTATION_Y);
                        outRotateAnim.setFloatValues(-90);
                        outRotateAnim.setDuration(500);

                        mVwTransitioner.transition(asIn, outRotateAnim, new ImageViewTransitionListener() {
                            @Override
                            public void onAcquired(ImageView view, boolean fromPool) {
                                // Init
                                view.setAlpha(0f);
                                view.setRotationY(90);
                                view.setImageDrawable(resource);
                            }
                        });

                    }
                });
                break;
            case EX_3:
                rb.into(new ViewTarget<View, Drawable>(mContainerLo) {
                    @Override
                    public synchronized void onResourceReady(final Drawable resource, Transition<? super Drawable> transition) {

                        // In anim
                        final ObjectAnimator inAlphaAnim = new ObjectAnimator();
                        inAlphaAnim.setProperty(View.ALPHA);
                        inAlphaAnim.setFloatValues(1f);
                        inAlphaAnim.setDuration(500);

                        final AnimatorSet asIn = new AnimatorSet();
                        asIn.playTogether(inAlphaAnim);

                        // Out anim
                        final ObjectAnimator outAlphaAnim = new ObjectAnimator();
                        outAlphaAnim.setProperty(View.ALPHA);
                        outAlphaAnim.setFloatValues(0.5f);
                        outAlphaAnim.setDuration(2500);

                        final ObjectAnimator outRotateXAnim = new ObjectAnimator();
                        outRotateXAnim.setProperty(View.ROTATION_X);
                        outRotateXAnim.setFloatValues(90);
                        outRotateXAnim.setDuration(2500);

                        final ObjectAnimator outRotateYAnim = new ObjectAnimator();
                        outRotateYAnim.setProperty(View.ROTATION_Y);
                        outRotateYAnim.setFloatValues(-45);
                        outRotateYAnim.setDuration(2500);

                        final ObjectAnimator outTransXAnim = new ObjectAnimator();
                        outTransXAnim.setProperty(View.TRANSLATION_X);
                        outTransXAnim.setFloatValues(-convertDipToPx(getContext(), 100));
                        outTransXAnim.setDuration(2500);

                        final ObjectAnimator outPivotXAnim = new ObjectAnimator();
                        outPivotXAnim.setPropertyName("PivotX");
                        outPivotXAnim.setFloatValues(45);
                        outPivotXAnim.setDuration(2500);

                        final AnimatorSet asOut = new AnimatorSet();
                        asOut.playTogether(outAlphaAnim, outRotateXAnim, outRotateYAnim, outTransXAnim, outPivotXAnim);

                        mVwTransitioner.transition(asIn, asOut, new ImageViewTransitionListener() {
                            @Override
                            public void onAcquired(ImageView view, boolean fromPool) {
                                // Init&Reset
                                view.setAlpha(0f);
                                view.setRotationY(0);
                                view.setRotationX(0);
                                view.setTranslationX(0);
                                view.setPivotX(0);
                                view.setImageDrawable(resource);
                            }
                        });

                    }
                });
                break;
            case EX_4:
                rb.into(new ViewTarget<View, Drawable>(mContainerLo) {
                    @Override
                    public synchronized void onResourceReady(final Drawable resource, Transition<? super Drawable> transition) {

                        // In anim
                        final ObjectAnimator inAlphaAnim = new ObjectAnimator();
                        inAlphaAnim.setProperty(View.ALPHA);
                        inAlphaAnim.setFloatValues(1f);
                        inAlphaAnim.setDuration(1000);

                        final ObjectAnimator inScaleXAnim = new ObjectAnimator();
                        inScaleXAnim.setProperty(View.SCALE_X);
                        inScaleXAnim.setFloatValues(1f);
                        inScaleXAnim.setDuration(1000);

                        final ObjectAnimator inScaleYAnim = new ObjectAnimator();
                        inScaleYAnim.setProperty(View.SCALE_Y);
                        inScaleYAnim.setFloatValues(1f);
                        inScaleYAnim.setDuration(1000);

                        final ObjectAnimator inTransYAnim = new ObjectAnimator();
                        inTransYAnim.setProperty(View.TRANSLATION_Y);
                        inTransYAnim.setFloatValues(0);
                        inTransYAnim.setDuration(1000);

                        final ObjectAnimator inRotationXAnim = new ObjectAnimator();
                        inRotationXAnim.setProperty(View.ROTATION_X);
                        inRotationXAnim.setFloatValues(0);
                        inRotationXAnim.setDuration(1000);

                        final AnimatorSet asIn = new AnimatorSet();
                        // asIn.setStartDelay(2500); // Wait for out anim finish.
                        asIn.playTogether(inAlphaAnim, inScaleXAnim, inScaleYAnim, inRotationXAnim, inTransYAnim);

                        // Out anim
                        final ObjectAnimator outAlphaAnim = new ObjectAnimator();
                        outAlphaAnim.setProperty(View.ALPHA);
                        outAlphaAnim.setFloatValues(0.5f);
                        outAlphaAnim.setDuration(1000);

                        final ObjectAnimator outScaleXAnim = new ObjectAnimator();
                        outScaleXAnim.setProperty(View.SCALE_X);
                        outScaleXAnim.setFloatValues(1.75f);
                        outScaleXAnim.setDuration(1000);

                        final ObjectAnimator outScaleYAnim = new ObjectAnimator();
                        outScaleYAnim.setProperty(View.SCALE_Y);
                        outScaleYAnim.setFloatValues(1.75f);
                        outScaleYAnim.setDuration(1000);

                        final ObjectAnimator outRotateXAnim = new ObjectAnimator();
                        outRotateXAnim.setProperty(View.ROTATION_X);
                        outRotateXAnim.setFloatValues(-90);
                        outRotateXAnim.setDuration(1000);

                        final ObjectAnimator outTransYAnim = new ObjectAnimator();
                        outTransYAnim.setProperty(View.TRANSLATION_Y);
                        outTransYAnim.setFloatValues(mContainerLo.getHeight() * 0.75f);
                        outTransYAnim.setDuration(1000);

                        final AnimatorSet asOut = new AnimatorSet();
                        asOut.playTogether(outAlphaAnim, outScaleXAnim, outScaleYAnim, outRotateXAnim, outTransYAnim);

                        mVwTransitioner.transition(asIn, asOut, new ImageViewTransitionListener() {
                            @Override
                            public void onAcquired(ImageView view, boolean fromPool) {
                                // Init&Reset
                                view.setAlpha(0f);
                                view.setRotationX(-90);
                                view.setTranslationY(-mContainerLo.getHeight() * 0.75f);
                                view.setScaleX(0.5f);
                                view.setScaleY(0.5f);
                                view.setImageDrawable(resource);
                            }
                        });

                    }
                });
                break;
            case EX_5:
                rb.into(new ViewTarget<View, Drawable>(mContainerLo) {
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
                        outSlideAnim.setFloatValues(-mContainerLo.getWidth());
                        outSlideAnim.setDuration(1000);

                        final ObjectAnimator outAlphaAnim = new ObjectAnimator();
                        outAlphaAnim.setProperty(View.ALPHA);
                        outAlphaAnim.setFloatValues(0f);
                        outAlphaAnim.setDuration(1000);

                        final AnimatorSet asOut = new AnimatorSet();
                        asOut.playTogether(outSlideAnim, outAlphaAnim, outRotateAnim);

                        mVwTransitioner.transition(asIn, asOut, new ImageViewTransitionListener() {
                            @Override
                            public void onAcquired(ImageView view, boolean fromPool) {
                                // Init
                                view.setTranslationX(mContainerLo.getWidth());
                                view.setAlpha(0f);
                                view.setRotationY(-90);
                                view.setImageDrawable(resource);
                            }
                        });

                    }
                });
                break;
        }

    }

    public int convertDipToPx(Context context, int dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return (int) px;
    }

}
