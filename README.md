# Glider
This is an android image loader wrapper for Glide.

If you are using Glide to load a picture in you Android app like this:
<pre><code>
Glide.with(this).load(picUrl).into(imageView);
</code></pre>

I think it's not simple enough, so I wrapped it by myself and now I can use Glide like this :
<pre><code>
Glider.load(imageView, picUrl);                 // load picture.
Glider.loadSquare(imageView, picUrl);           // load crop square picture
Glider.loadCircle(imageView, picUrl);           // load crop circle picture
Glider.loadSquareRound(imageView, picUrl, 10);  // load crop square picture rounded 10dp.
// by yourself.
Glider.load(new GliderOption.Builder(imageView, picUrl)
      .crossFade(666)
      .diskCache(false)
      .error(R.mipmap.ic_launcher)
      .loading(R.mipmap.ic_launcher)
      .transformCircle()
//    .listener()
      .create());
</code></pre>
Just need to copy few java files into your app or add a 'compile' as below to use it.
<pre><code>
compile 'com.zozx.android:glider:1.0.0'
</code></pre>
If you use above 'compile' to use Glider, you should remove old Glide 'compile' in your gradle file.
And if you copy the java files into your app , you should make sure these two 'compile' in your app/build.gradle
<pre><code>
compile 'com.github.bumptech.glide:glide:3.7.0'
compile 'jp.wasabeef:glide-transformations:2.0.1'
</code></pre>
