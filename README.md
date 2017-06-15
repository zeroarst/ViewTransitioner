

# ViewTransitioner
[![ ](https://jitpack.io/v/zeroarst/ViewTransitioner.svg)](https://jitpack.io/#zeroarst/ViewTransitioner)


Allow you to transition between views smoonthly with in &amp; out animations without interrupting each other. This uses Pool mechanism.

It is recommend to use along with https://github.com/bumptech/glide which save you trouble of OOM. 

Check the ViewTransitionExample to see use them together. 

Tip: In the example it does not use `Bitmap` pool in order to avoid resuing bitmap that is still in animating.

Use
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
![1](https://github.com/zeroarst/zeroarst.github.io/blob/master/viewtransitioner/e1.gif)
![2](https://github.com/zeroarst/zeroarst.github.io/blob/master/viewtransitioner/e2.gif)
![3](https://github.com/zeroarst/zeroarst.github.io/blob/master/viewtransitioner/e3.gif)
![4](https://github.com/zeroarst/zeroarst.github.io/blob/master/viewtransitioner/e4.gif)
![5](https://github.com/zeroarst/zeroarst.github.io/blob/master/viewtransitioner/e5.gif)
