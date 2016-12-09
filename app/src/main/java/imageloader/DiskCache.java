package imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dc on 2016/12/9.
 *
 * 1.1  新增的本地缓存类
 *
 *
 */

public class DiskCache {


    static  String cacheDir = "sdcard/cache/";



    public Bitmap  get(String url){

        return BitmapFactory.decodeFile(cacheDir+url);  //从文件地址中获取

    }



    public void  put (String url ,Bitmap bmp){

        FileOutputStream fileOutputStream = null ;

        try {
            fileOutputStream = new FileOutputStream(cacheDir+url);
            bmp.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }finally {

            if (fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }






}
