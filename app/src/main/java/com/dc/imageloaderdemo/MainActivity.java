package com.dc.imageloaderdemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import imageloader.DoubleCache;
import imageloader.ImageCache;
import imageloader.ImageLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageLoader imageLoader = new ImageLoader();


        imageLoader.setmImageCache(new DoubleCache());  //接口 实现可扩展性

        imageLoader.setmImageCache(new ImageCache() {  //实现自定义的缓存策略
            @Override
            public Bitmap get(String url) {
                return null;
            }

            @Override
            public void put(String url, Bitmap bitmap) {

            }
        });



    }
}
