package imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dc on 2016/12/8.
 * <p>
 * 图片加载框架 1.0 版本
 * 1.1   增加本地文件缓存
 */

public class ImageLoader {


    //内存缓存
    ImageCache mImageCache = new ImageCache();


    DiskCache mDiskCache = new DiskCache(); //本地缓存
    //双缓存
    DoubleCache mDoubleCache = new DoubleCache();


    boolean isUseDiskCache = false;
    //使用双缓存
    boolean isDoubleCache = false;


    //线程池的数量 cpu数量
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    /**
     * @param useDiskCache 是否启用 本地缓存
     */
    public void UseDiskCache(boolean useDiskCache) {
        isUseDiskCache = useDiskCache;
    }

    /**
     * @param useDoubleCache 是否启用 双缓存
     */
    public void UseDoubleCache (boolean useDoubleCache ) {
        isDoubleCache = useDoubleCache;
    }


    public void dispalyImage(final String url, final ImageView imageView) {

        //Bitmap bitmap = mImageCache.get(url);  1.0版本 只有内存缓存

        Bitmap bitmap = null;
        if (isDoubleCache){
            bitmap = mDoubleCache.get(url);
        }else if (isUseDiskCache){
            bitmap = mDiskCache .get(url);
        }else {
            bitmap = mImageCache.get(url);
        }


        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);

            return;
        }

        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(url);

                if (bitmap == null) {
                    return;
                }

                if (imageView.getTag().equals(url)) {
                    imageView.setImageBitmap(bitmap);

                }
                mImageCache.put(url, bitmap);
            }
        });

    }

    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;

        try {
            URL url = new URL(imageUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(con.getInputStream());
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
