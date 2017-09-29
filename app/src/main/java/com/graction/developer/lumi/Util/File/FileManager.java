package com.graction.developer.lumi.Util.File;

/**
 * Created by graction03 on 2017-09-29.
 */

public class FileManager {
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
}
