package com.graction.developer.lumi.Util.File;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.graction.developer.lumi.Util.Log.LogManager;
import com.graction.developer.lumi.Util.Thread.ThreadManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by graction03 on 2017-09-29.
 */

public class BaseActivityFileManager {
    private static final BaseActivityFileManager ourInstance = new BaseActivityFileManager();
    private static Activity activity;
    private ThreadManager threadManager;
    private byte[] buf;

    public static BaseActivityFileManager getInstance() {
        return ourInstance;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Bitmap encodeFileToBitmap(File file) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (Exception e) {
            LogManager.log(getClass(), "encodeFileToBitmap(File file)", e);
        }
        return bitmap;
    }

    public byte[] encodeBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        } catch (Exception e) {
            LogManager.log(getClass(), "encodeBitmapToFile(Bitmap bitmap)", e);
        }
        return bytes.toByteArray();
    }


    public byte[] getByteFromURL(String url) throws InterruptedException {
        threadManager = new ThreadManager(
                () -> {
                    try {
                        URL u = new URL(url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) u.openConnection();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        buf = new byte[inputStream.available()];
                        inputStream.read(buf);
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        threadManager.threadComplete();
                    }
                },
                new ThreadManager.ThreadComplete() {
                    @Override
                    public byte[] complete() {
                        return buf;
                    }
                }
        );
        buf = threadManager.run();
        return buf;
    }

    private byte[] getByteFromURL2(String url) {
        byte[] buf = null;
        try {
            URL u = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) u.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            OutputStream outStream = httpURLConnection.getOutputStream();
            // 읽어들일 버퍼크기를 메모리에 생성
            int len = 0;
            // 끝까지 읽어들이면서 File 객체에 내용들을 쓴다
            while ((len = inputStream.read(buf)) > 0) {
                outStream.write(buf, 0, len);
            }
//            File file = new File();
            // Stream 객체를 모두 닫는다.
            outStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buf;
    }

    public Bitmap getBitmapFromURL(String url) throws InterruptedException {
        return convertByteArrayToBitmap(getByteFromURL(url));
    }
    public Bitmap convertByteArrayToBitmap(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public void copyAssetAll(String srcPath) {
        AssetManager assetMgr = activity.getAssets();
        String assets[] = null;
        try {
            assets = assetMgr.list(srcPath);
            if (assets.length == 0) {
                copyFile(srcPath);
            } else {
                String destPath = activity.getFilesDir().getAbsolutePath() + File.separator + srcPath;
                File dir = new File(destPath);
                if (!dir.exists())
                    dir.mkdir();
                for (String element : assets) {
                    copyAssetAll(srcPath + File.separator + element);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void copyFile(String srcFile) {
        AssetManager assetMgr = activity.getAssets();

        InputStream is = null;
        OutputStream os = null;
        try {
            String destFile = activity.getFilesDir().getAbsolutePath() + File.separator + srcFile;

            is = assetMgr.open(srcFile);
            os = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = is.read(buffer)) != -1) {
                os.write(buffer, 0, read);
            }
            is.close();
            os.flush();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFilePath() {
        return activity.getFilesDir().getAbsolutePath() + File.separator;
    }

    /*private void a(){
        String externalDirPath = context.getExternalFilesDir("").getAbsolutePath() + "/", path = "images/background/",
                fileName =  DataStorage.weathers.get(weather.getId()).getImage();

        logger.log(HLogger.LogType.INFO, "external : " + externalDirPath);
        logger.log(HLogger.LogType.INFO, "path : " + path);
        File externalDir= new File(externalDirPath);
        File outfile = new File(externalDirPath + fileName);
        InputStream is = null;
        FileOutputStream fo = null;
        long filesize = 0;
        try {
            if(!externalDir.exists()){
                externalDir.mkdir();
            }
//            is = context.getAssets().open(path, AssetManager.ACCESS_BUFFER);
//            is = context.getAssets().open(path);
//            is = context.getAssets().open("images/background/sunny.jpg");
            is = context.getAssets().open("sunny.jpg");
            filesize = is.available();
            if (outfile.length() <= 0) {
                byte[] tempdata = new byte[(int) filesize];
                is.read(tempdata);
                is.close();
                outfile.createNewFile();
                fo = new FileOutputStream(outfile);
                fo.write(tempdata);
                fo.close();
            }
        } catch (IOException e) {
            logger.log(HLogger.LogType.ERROR, "setWeatherBackground(Context context, ImageView imageView, Weather weather)", "file error", e);
        }
    }*/

    public Drawable getDrawableFromAssets(AssetManager assetManager, String path) {
        InputStream is = null;

        try {
            is = assetManager.open(path);
            // TODO : use is(InputStream).

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (is != null) {
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Bitmap b = BitmapFactory.decodeStream(is);
        b.setDensity(Bitmap.DENSITY_NONE);
        Drawable d = new BitmapDrawable(b);
        return d;
    }

    public File getFile(String path) {
        File file = null;
        try {
            file = new File("file:///android_asset/" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /*
    *
    * ex) path : images/background/test.gif
    *
    * */
    public byte[] getAssetFileToByte(AssetManager assetManager, String path) {
        InputStream inputStream;
        byte[] fileBytes = null;
        try {
            inputStream = assetManager.open(path);
            fileBytes = new byte[inputStream.available()];
            inputStream.read(fileBytes);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileBytes;
    }


}

 /* public void doFileUpload(File file, String method) {

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        DataInputStream inStream = null;
//        String existingFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mypic.png";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        final int maxBufferSize = 1 * 1024 * 1024;
        String responseFromServer = "";
        String urlString = Net.getU();
        try {
            //------------------ CLIENT REQUEST
            FileInputStream fileInputStream = new FileInputStream(file);
            // open a URL connection to the Servlet
            URL url = new URL(urlString + method);
            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            // Allow Inputs
            conn.setDoInput(true);
            // Allow Outputs
            conn.setDoOutput(true);
            // Don't use a cached copy.
            conn.setUseCaches(false);
            // Use a post method.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + file.getName() + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            // close streams
            Log.e("Debug", "File is written");
            fileInputStream.close();
            dos.flush();
            dos.close();
        } catch (MalformedURLException ex) {
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        } catch (IOException ioe) {
            Log.e("Debug", "error: " + ioe.getMessage(), ioe);
        }
        //------------------ read the SERVER RESPONSE
        try {
            inStream = new DataInputStream(conn.getInputStream());
            String str;
            while ((str = inStream.readLine()) != null) {
                Log.e("Debug", "Server Response " + str);
            }
            inStream.close();
        } catch (IOException ioex) {
            Log.e("Debug", "error: " + ioex.getMessage(), ioex);
        }
    }*/