package imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by dc on 2016/12/8.
 *
 * 图片缓存类
 *
 */

public class ImageCache {

    LruCache<String,Bitmap>   mImageCache ;

    public ImageCache(){
        initImageCache();
    }

    private void initImageCache() {

          //计算可使用的最大内存
       final int  maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        //4分之一作为缓存
        final int cacheSize = maxMemory/4;


        mImageCache = new LruCache<String,Bitmap>(cacheSize){

            @Override
            protected int sizeOf(String key, Bitmap value) {

                return value.getRowBytes() * value.getHeight()/1024;
            }
        };
    }



    public  void  put(String url ,Bitmap bitmap){

        mImageCache.put(url,bitmap);

    }


    public Bitmap  get (String url){


        return  mImageCache.get(url);
    }


}
