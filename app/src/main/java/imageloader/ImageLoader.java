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


    //接口对象，实例化一个默认子类
    ImageCache mImageCache = new MemoryCache();

    //线程池的数量 cpu数量
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    /**依赖注入
     * @param cache  接口 供外部掉用
     */
    public  void  setmImageCache(ImageCache cache){

        mImageCache = cache;

    }



    public void dispalyImage(final String url, final ImageView imageView) {

        Bitmap bitmap = mImageCache.get(url); //默认的接口子类 内存缓存去实现

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);

            return;
        }
        submitLoadRequest(url,imageView);
    }


    /**
     *
     * 提交到线程池呈现
     * @param url
     * @param imageView
     */
    private void submitLoadRequest(final String url, final ImageView imageView) {
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
