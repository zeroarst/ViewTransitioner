package com.zeroarst.viewtransitioner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;


public class ImageViewTransitioner extends ViewTransitioner<ImageView> {

    public ImageViewTransitioner(@NonNull Context context, int poolSize) {
        super(context, poolSize);
    }

    public ImageViewTransitioner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public ImageView createView() {
        return new ImageView(getContext());
    }

}