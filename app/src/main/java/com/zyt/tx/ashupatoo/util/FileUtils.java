package com.zyt.tx.ashupatoo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;


import android.graphics.Bitmap;
import android.util.Log;

public class FileUtils {
    public static final String LOCAL_FILE_PREFIX = "file://";
    private static FileUtils fileUtils;

    private FileUtils() {
    };

    public static FileUtils getInstance() {
        if (fileUtils == null) {
            fileUtils = new FileUtils();
        }
        return fileUtils;
    }

    public void copyFile(String src, String des) {
        if (src == null || src.length() == 0 || des == null
                || des.length() == 0) {
            return;
        }
        try {
            // 旧地址
            FileChannel srcChannel = new FileInputStream(src).getChannel();
            // 新地址
            FileChannel dstChannel = new FileOutputStream(des).getChannel();
            // Copy file contents from source to destination
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            // Close the channels
            srcChannel.close();
            dstChannel.close();
        } catch (IOException e) {

        }
    }

    public boolean delFile(String src) {
        boolean suc = false;
        if (src == null || src.length() == 0) {
            return false;
        }
        File srcFile = new File(src);
        if (srcFile != null) {
            suc = srcFile.delete();
        }
        return suc;
    }

    public boolean renameFile(String src, String des) {
        boolean suc = false;
        if (src == null || src.length() == 0 || des == null
                || des.length() == 0) {
            return false;
        }
        File srcFile = new File(src);
        if (srcFile != null) {
            suc = srcFile.renameTo(new File(des));
        }
        return suc;
    }

    public boolean createFile(String file, InputStream in) {
        if (in == null || file == null || file.length() == 0) {
            return false;
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        int data;
        try {
            data = in.read();
            while (data != -1) {
                outputStream.write(data);
                data = in.read();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                return false;
            }
        }
        Log.d("test", "creatFile succ:" + file);
        return true;
    }

    public boolean createFile(Bitmap bitmap, String path) throws Exception {
        if (bitmap == null || path == null || path.length() == 0) {
            return false;
        }
        File f = new File(path);
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

//    public boolean createFileFromNet(String netUrl, String localPath) {
//
//        if (netUrl != null && netUrl.length() > 0 && localPath != null
//                && localPath.length() > 0) {
//            InputStream inputStream = HttpRequestThread.downloadFile(netUrl,
//                    5000, 5000);
//            if (inputStream != null) {
//                return createFile(localPath, inputStream);
//            }
//        }
//        return false;
//    }

    public boolean isFile(String path) {
        if (path == null || path.length() == 0) {
            return false;
        }
        File file = new File(path);
        return file.isFile();
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }
}
