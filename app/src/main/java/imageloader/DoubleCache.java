package imageloader;

import android.graphics.Bitmap;

/**
 * Created by dc on 2016/12/9.
 *
 * 1.1   缓存策略   先获取内存缓存   没有再从sd卡获取缓存 ，最后才是网络
 *
 */

public class DoubleCache  implements ImageCache{

    ImageCache mMemoryCache  = new MemoryCache();  //接口  实例化 一个实现接口

    ImageCache mDiskCache = new DiskCache();

    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = mMemoryCache.get(url);

        if (bitmap==null){
            bitmap = mDiskCache.get(url);
        }

        return bitmap;
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        mDiskCache.put(url,bitmap);
        mMemoryCache.put(url,bitmap);
    }



}
