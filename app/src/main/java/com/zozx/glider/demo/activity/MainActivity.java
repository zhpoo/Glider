package com.zozx.glider.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zozx.glider.Glider;
import com.zozx.glider.GliderOption;
import com.zozx.glider.demo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        String picUrl = "";
        Glide.with(this).load(picUrl).into(imageView);
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
//                .listener()
                .create());
    }
}
