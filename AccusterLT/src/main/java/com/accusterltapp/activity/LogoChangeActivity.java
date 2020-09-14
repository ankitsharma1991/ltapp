package com.accusterltapp.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.accusterltapp.model.Heleprec;
import com.accusterltapp.model.ReportSettingUpdate;
import com.accusterltapp.service.ApiClinet;
import com.accusterltapp.service.ApiConstant;
import com.accusterltapp.service.ImgService;
import com.base.activity.BaseActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.accusterltapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class LogoChangeActivity extends BaseActivity {
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    SharedPreferences pref;
    private static final int REQUEST_PERMISSION_SETTING = 101;
     ImageView image;
     EditText address;
     boolean sentToSettings=false;
     boolean imageflag=false;
     Uri filePath;
    Call<ReportSettingUpdate> userCall;
     String add;
     Bitmap bitmap;
     File file,destination;
     String imagestring,imgPath;
     String camp_id,header;
     android.widget.ProgressBar progressb;
     ProgressDialog progress;
  //  private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private SharedPreferences permissionStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logochangeactivity);
        setTitle("Update Report");
        progressb= findViewById(R.id.progress);
        //setActionBar(R.id.include_toolbar);
        //setToolBarTittle("ASyn List");

        image=(ImageView)findViewById(R.id.logoi);
        add=getIntent().getExtras().getString("address");
        header=getIntent().getExtras().getString("header");

        String name=getIntent().getExtras().getString("url");
        Log.e("name of image ",name);
        camp_id=getIntent().getExtras().getString("camp_id");
        if(!name.equals(""))
       Glide.with(this).load(ApiConstant.imageurl+name).diskCacheStrategy(DiskCacheStrategy.ALL).into(image);

        address=(EditText)findViewById(R.id.address);
        if(!name.equals(""))
        Glide.with(this).load(ApiConstant.imageurl+name).diskCacheStrategy(DiskCacheStrategy.ALL).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                progressb.setVisibility(View.GONE);
                image.setImageResource(R.drawable.logo);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressb.setVisibility(View.GONE);
                return false;
            }
        }).into(image);
        else {
            progressb.setVisibility(View.GONE);
            image.setImageResource(R.drawable.logo);
            Log.e("logo ","default");
        }
        address.setText(getIntent().getExtras().getString("address"));
        permissionStatus = this.getSharedPreferences("permissionStatus",this. MODE_PRIVATE);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getApplicationContext(), permissionsRequired[4]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[3])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[4])) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new  AlertDialog.Builder(LogoChangeActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(LogoChangeActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new  AlertDialog.Builder(LogoChangeActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Location permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", LogoChangeActivity.this.getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getApplicationContext(), "Go to Permissions to Grant  Camera and Location", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }

          ///  UtilityMethod.showShortToastMessage(getContext(), "Permissions Required");

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0], true);
            editor.commit();
        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 100);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {

            try {
                Uri selectedImage = data.getData();
                Log.e("nskldnv", selectedImage + "");
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");

                destination = new File(getRealPathFromURI(selectedImage).toString());

                 imgPath = createDirectoryAndSaveFile(bitmap, destination.getName()).getAbsolutePath();
                //workInAppPreference.setValuesSession(WorkInAppPreference.USER_PROFILE, imgPath);
                image.setImageBitmap(bitmap);
                // tb_tv_save_edit.setText("Edit");
                //  saveEdit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        }
        public String getStringImage (Bitmap bmp){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            byte[] bit = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap b = BitmapFactory.decodeByteArray(bit, 0, bit.length);
            image.setImageBitmap(b);

            return encodedImage;

        }


        public String getRealPathFromURI(Uri contentUri) {
            String[] proj = {MediaStore.Audio.Media.DATA};
            Cursor cursor = managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
    private File createDirectoryAndSaveFile(Bitmap imgSave, String fileName) {

        File direct = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name));

        if (!direct.exists()) {
            File imageDirectory = new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + "/");
            imageDirectory.mkdirs();
        }

        destination = new File(new File(Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + "/"), fileName);
        if (destination.exists()) {
            destination.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(destination);
            imgSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return destination;
    }
    public void save(View v) {
try {
    progress = new ProgressDialog(this);
    progress.setCancelable(true);
    progress.setMessage("Saving Logo ...");
    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    progress.show();
    ImgService service = ApiClinet.getClinet().create(ImgService.class);
    if(imgPath!=null) {

        File file = new File(imgPath);
        Log.e("path",imgPath);
        Log.e("hhhhhihaidso", file + "");
        RequestBody camp_id = RequestBody.create(MediaType.parse("text/plain"), this.camp_id);
        RequestBody header = RequestBody.create(MediaType.parse("text/plain"), this.header);
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), this.address.getText().toString());
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        RequestBody imgname = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        userCall = service.postImage(camp_id, address, header, body, imgname);
    }
    else {
        RequestBody camp_id = RequestBody.create(MediaType.parse("text/plain"), this.camp_id);
        RequestBody header = RequestBody.create(MediaType.parse("text/plain"), this.header);
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), this.address.getText().toString());
        userCall = service.postImage(camp_id, address, header, null, null);

    }

    // pDialog.show();
    userCall.enqueue(new Callback<ReportSettingUpdate>() {
        @Override
        public void onResponse(Call<ReportSettingUpdate> call, retrofit2.Response<ReportSettingUpdate> response) {
            Toast.makeText(LogoChangeActivity.this,"Done",Toast.LENGTH_LONG).show();
            Log.e("response ",response.toString());
            progress.dismiss();
            Heleprec.update=true;
            Heleprec.getCamList(LogoChangeActivity.this.getBaseContext());

        }

        @Override
        public void onFailure(Call<ReportSettingUpdate> call, Throwable t) {
            progress.dismiss();

            Toast.makeText(LogoChangeActivity.this, "Log updatae failed "+call.toString(), Toast.LENGTH_LONG).show();
        }

    });
}
catch (Exception e)
{

}


    }


}
