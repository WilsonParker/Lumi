package com.graction.developer.lumi.Util.File;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.graction.developer.lumi.Util.Log.HLogger;
import com.graction.developer.lumi.Util.Thread.ThreadManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.graction.developer.lumi.Util.Log.HLogger.LogType.INFO;

/**
 * Created by graction03 on 2017-09-29.
 */

public class BaseActivityFileManager {
    private static final BaseActivityFileManager ourInstance = new BaseActivityFileManager();
    private Activity activity;
    private AssetManager assetManager;
    private final boolean OverrideDeault = false;

    private ThreadManager threadManager;
    private byte[] buf;

    public static BaseActivityFileManager getInstance() {
        return ourInstance;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
        assetManager = activity.getAssets();
    }

    public Bitmap encodeFileToBitmap(File file) throws FileNotFoundException {
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
        return bitmap;
    }

    public byte[] encodeBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return bytes.toByteArray();
    }


    private HLogger logger = new HLogger(BaseActivityFileManager.class);

    public byte[] getByteFromURL(String url) throws InterruptedException {
        threadManager = new ThreadManager(
                () -> {
                    try {
                       /* URL u = new URL(url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) u.openConnection();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        buf = new byte[inputStream.available()];
                        logger.log(HLogger.LogType.INFO, "byte[] getByteFromURL(String)", "buf is null 1?" + (buf == null));
                        logger.log(HLogger.LogType.INFO, "byte[] getByteFromURL(String)", "buf is length 1?" + (buf.length));
                        inputStream.read(buf);
                        inputStream.close();*/

                        URL u = new URL(url);
                        InputStream inputStream = u.openStream();
                        buf = new byte[u.openConnection().getContentLength()];
                        inputStream.read(buf);
                        inputStream.close();
                        logger.log(INFO, "byte[] getByteFromURL(String)", "buf is null 1?" + (buf == null));
                        logger.log(INFO, "byte[] getByteFromURL(String)", "buf is length 1?" + (buf.length));
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
        logger.log(INFO, "byte[] getByteFromURL(String)", "buf is null 2?" + (buf == null));
        logger.log(INFO, "byte[] getByteFromURL(String)", "buf is length 2?" + (buf.length));

        return buf;
    }

    private byte[] getByteFromURL2(String url) throws IOException {
        byte[] buf = null;
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
        return buf;
    }

    public Bitmap getBitmapFromURL(String url) throws InterruptedException {
        return convertByteArrayToBitmap(getByteFromURL(url));
    }

    public Bitmap convertByteArrayToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public void copyAssetAll(String srcPath) throws IOException {
        AssetManager assetMgr = activity.getAssets();
        String assets[] = null;
        assets = assetMgr.list(srcPath);
        if (assets.length == 0) {
            copyAssetFromStorage(srcPath);
        } else {
            String destPath = activity.getFilesDir().getAbsolutePath() + File.separator + srcPath;
            File dir = new File(destPath);
            if (!dir.exists())
                dir.mkdir();
            for (String element : assets) {
                copyAssetAll(srcPath + File.separator + element);
            }
        }
    }


    public void copyAssetFromStorage(String srcFile) throws IOException {
        AssetManager assetMgr = activity.getAssets();
        String destFile = activity.getFilesDir().getAbsolutePath() + File.separator + srcFile;
        InputStream is = assetMgr.open(srcFile);
        OutputStream os = new FileOutputStream(destFile);
        byte[] buffer = new byte[1024];
        int read;
        while ((read = is.read(buffer)) != -1) {
            os.write(buffer, 0, read);
        }
        is.close();
        os.flush();
        os.close();

    }

    public Bitmap getBitmapFromAssets(AssetManager assetManager, String path) throws IOException {
        InputStream is = assetManager.open(path);
        if (is != null)
            is.close();
        Bitmap b = BitmapFactory.decodeStream(is);
        return b;
    }

    public Bitmap getBitmapFromAssets(String path) throws IOException {
        return getBitmapFromAssets(assetManager, path);
    }

    public Bitmap convertDrawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null)
                return bitmapDrawable.getBitmap();
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public Drawable getDrawableFromAssets(AssetManager assetManager, String path) throws IOException {
        Bitmap b = getBitmapFromAssets(assetManager, path);
        b.setDensity(Bitmap.DENSITY_NONE);
        Drawable d = new BitmapDrawable(b);
        return d;
    }

    public Drawable getDrawableFromAssets(String path) throws IOException {
        return getDrawableFromAssets(assetManager, path);
    }

    /*
    *
    * ex) path : images/background/test.gif
    *
    * */
    public byte[] getAssetFileToByte(AssetManager assetManager, String path) throws IOException {
        InputStream inputStream = assetManager.open(path);
        byte[] fileBytes = new byte[inputStream.available()];
        inputStream.read(fileBytes);
        inputStream.close();
        return fileBytes;
    }

    public byte[] getAssetFileToByte(String path) throws IOException {
        return getAssetFileToByte(assetManager, path);
    }

    public void saveFile(String path, String url) throws IOException, InterruptedException {
        saveFile(path, getByteFromURL(url), OverrideDeault);
    }

    public void saveFile(String path, String url, boolean override) throws IOException, InterruptedException {
        saveFile(path, getByteFromURL(url), override);
    }

    // is work?
    public void saveFile(String path, File file) throws IOException {
        saveFile(path, file, OverrideDeault);
    }

    public void saveFile(String path, File file, boolean override) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        out.flush();
        out.close();
    }

    public void saveFile(String path, byte[] data) throws IOException {
        saveFile(path, data, OverrideDeault);
    }

    public void saveFile(String path, byte[] data, boolean override) throws IOException {
        logger.log(INFO, "saveFile(String path, byte[] data, boolean override) ", "path : " + getPath() + path);
        FileOutputStream out = activity.openFileOutput("sunny.png", Context.MODE_PRIVATE);
        out.write(data);
        out.close();
    }

    public void saveDrawable(Drawable drawable, String file) throws FileNotFoundException {
        File save = new File(file);
        FileOutputStream fos = new FileOutputStream(save);
        Bitmap bitmap = convertDrawableToBitmap(drawable);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
    }

    public boolean assetExists(String path) {
        boolean result = true;
        try {
            assetManager.open(path);
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public String getPath() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? activity.getExternalFilesDir(null).getAbsolutePath() : activity.getFilesDir().getAbsolutePath();
    }

    public String getFilePath() {
        return activity.getFilesDir().getAbsolutePath() + File.separator;
    }

    private String[] separatePathAndName(String file) {
        String[] result = new String[2];
        result[0] = file.substring(0, file.lastIndexOf("/") + 1);
        result[1] = file.substring(file.lastIndexOf("/") + 1);
        return result;
    }

    public boolean makeDirectory(String path) {
        File dir = new File(getPath() + path);
        if (!dir.exists())
            return dir.mkdirs();
        else return false;
    }

    public boolean isExists(String file) {
        return new File(getPath() + file).exists();
    }
}

 /*
    @Deprecated
    public File getFile(String path) {
        File file = null;
        try {
            file = new File("file:///android_asset/" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }*/

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