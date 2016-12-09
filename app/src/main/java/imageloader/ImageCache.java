package imageloader;

import android.graphics.Bitmap;

/**
 * Created by dc on 2016/12/9.
 * 1.1  用 if 来判断 使用哪种 缓存  没有扩展性
 *
 * 使用接口  具备扩展性
 */

public interface ImageCache {

      Bitmap  get(String url );

    void  put(String url ,Bitmap bitmap);



}
