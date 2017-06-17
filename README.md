

# ViewTransitioner
[![ ](https://jitpack.io/v/zeroarst/ViewTransitioner.svg)](https://jitpack.io/#zeroarst/ViewTransitioner)


Allow you to transition between views smoonthly by applying in &amp; out animations on each `View` without interrupting each other. This uses Pool mechanism.

If you want to transition ImageView I would recommend using [Glide][1] which save you trouble of OOM. 
Check the ViewTransitionExample to see how to use them together. 
Tip: In the example it does not use `Bitmap` pool in Glide in order to avoid resuing bitmap that is still in animating, which causes a transition glitch.

Usage
--------
Step 1. Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
Step 2. Add the dependency
```gradle
dependencies {
  compile 'com.github.zeroarst:ViewTransitioner:0.3'
}
```

```
ViewTransitioner<ImageView> vwTransitioner = new ViewTransitioner<>(mViewContainerLayout);
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

vwTransitioner.transition(inAlphaAnim, outAlphaAnim, new ImageViewTransitionListener() {
  @Override
  public void onAcquired(ImageView view, boolean fromPool) {
      // Init
      view.setAlpha(0f);
      view.setImageDrawable(resource);
  }
});
```
![1](https://github.com/zeroarst/zeroarst.github.io/blob/master/viewtransitioner/e1.gif)
![2](https://github.com/zeroarst/zeroarst.github.io/blob/master/viewtransitioner/e2.gif)
![3](https://github.com/zeroarst/zeroarst.github.io/blob/master/viewtransitioner/e3.gif)
![4](https://github.com/zeroarst/zeroarst.github.io/blob/master/viewtransitioner/e4.gif)
![5](https://github.com/zeroarst/zeroarst.github.io/blob/master/viewtransitioner/e5.gif)



[1]: https://github.com/bumptech/glide/releases
