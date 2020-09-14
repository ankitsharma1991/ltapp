package com.base.utility;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.accusterltapp.BuildConfig;
import com.accusterltapp.database.AppConstants;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dilip on 19/12/14.<Br>
 * Profile Picture utilities.<Br>
 *
 * @author Dilip.Kumar.Chaudhary
 */
public class ImageCacheUtil {
    private static final String JPEG_FILE_SUFFIX = ".jpg";


    public static void captureFromCamera(Activity activity, String folderName, String fileName, int requestType) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            File file = getProfileFile(folderName, fileName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    activity.grantUriPermission(packageName, Uri.fromFile(file), Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                            Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(
                        activity, "com.accusterltapp.provider", file));

            } else {
                takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            }
            activity.startActivityForResult(takePictureIntent, requestType);
        }
    }

    public static void selectFromGallery(Activity activity, int requestType) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        activity.startActivityForResult(galleryIntent, requestType);
    }

    public static String copyFile(String folderName, String fileName, File sourceFile) {
        File destinationFile = getProfileFile(folderName, fileName);
        try {
            InputStream instream = new FileInputStream(sourceFile);
            OutputStream outstream = new FileOutputStream(destinationFile);
            byte[] buffer = new byte[1024];

            int length;
            /*copying the contents from input stream to
             * output stream using read and write methods
    	     */
            while ((length = instream.read(buffer)) > 0) {
                outstream.write(buffer, 0, length);
            }
            //Closing the input/output file streams
            instream.close();
            outstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return destinationFile.getAbsolutePath();
    }

    public static File getProfileFile(String folderName, String fileName) {
        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + folderName);
        if (!folder.exists()) {
            folder.mkdir();
        }
        return new File(folder + "/" + fileName + ImageCacheUtil.JPEG_FILE_SUFFIX);
    }

    public static String getImageName(String imagePath) {
        String[] folders = imagePath.split("/");
        if (folders.length > 0) {
            return folders[(folders.length - 1)];
        }
        return "";
    }

    public static void saveProfileImageInLocalFile(Bitmap bitmap, String filePath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bos);
            bos.close();
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> getFromSdcard() {
        File file = new File(android.os.Environment.getExternalStorageDirectory(), AppConstants.TEST_REPORT_IMAGE);
        ArrayList<String> mArrayList = new ArrayList<>();
        if (file.isDirectory()) {
            File[] listFile = file.listFiles();
            if (listFile != null && listFile.length > 0)
                for (File aListFile : listFile) {
                    mArrayList.add(aListFile.getAbsolutePath());
                }
        }
        return mArrayList;
    }
}
