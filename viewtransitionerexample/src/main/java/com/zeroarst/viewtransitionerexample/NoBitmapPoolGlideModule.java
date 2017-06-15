package com.zeroarst.viewtransitionerexample;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPoolAdapter;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public class NoBitmapPoolGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setBitmapPool(new BitmapPoolAdapter());
    }
}
