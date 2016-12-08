package imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dc on 2016/12/8.
 *
 * 图片加载框架 1.0 版本
 */

public class ImageLoader {

     ImageCache  mImageCache  = new ImageCache();

    //线程池的数量 cpu数量
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


    public void  dispalyImage(final String url , final ImageView imageView){

        Bitmap bitmap = mImageCache.get(url);


        if (bitmap!=null){
            imageView.setImageBitmap(bitmap);

            return;
        }

        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(url);

                if (bitmap == null){
                    return;
                }

                if (imageView.getTag().equals(url)){
                    imageView.setImageBitmap(bitmap);

                }
                mImageCache.put(url,bitmap);
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
