package imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by dc on 2016/12/9.
 *
 * 1.1   缓存策略   先获取内存缓存   没有再从sd卡获取缓存 ，最后才是网络
 *
 */

public class DoubleCache {

    ImageCache mMemoryCache  = new ImageCache();

    DiskCache mDiskCache = new DiskCache();


    public Bitmap get (String url ){
        Bitmap bitmap = mMemoryCache.get(url);

        if (bitmap==null){
            bitmap = mDiskCache.get(url);
        }

        return bitmap;
    }


    public void  put (String url , Bitmap  bitmap){

        mDiskCache.put(url,bitmap);
        mMemoryCache.put(url,bitmap);

    }




}
