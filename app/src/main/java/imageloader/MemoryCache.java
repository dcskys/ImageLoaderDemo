package imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by dc on 2016/12/8.
 *
 * 图片缓存类
 *
 *
 * 1.0 有个问题  LruCache 内存缓存
 * 当应用重新启动时， 原来加载的图片就会丢失  就会重新下载
 * 需要将下载的内存存入本地  ，增加个本地缓存，，遵循开闭原则
 *
 */

public class MemoryCache  implements ImageCache {

    LruCache<String,Bitmap>   mImageCache ;  //内存缓存  ，

    public MemoryCache(){
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

    @Override
    public Bitmap get(String url) {
        return  mImageCache.get(url);
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        mImageCache.put(url,bitmap);

    }



}
