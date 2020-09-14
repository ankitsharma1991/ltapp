package com.accusterltapp.register;//package com.ltapp.register;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Base64;
//import android.util.Log;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.ltapp.R;
//import com.ltapp.activity.DeviceListActivity;
//import com.ltapp.login.LoginActivity;
//import com.ltapp.model.Registration;
//import com.ltapp.model.Session;
//import com.ltapp.parse.AsyncTaskCompleteListener;
//import com.ltapp.parse.HttpRequester;
//import com.ltapp.realm.RealmController;
//import com.base.utility.AJUtils;
//import com.base.utility.Const;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.util.HashMap;

//public class RegisterActivity extends AppCompatActivity  {
//
//    private EditText et_email, et_password, et_cpassword, et_contact, et_name;
//    private ImageView img_profile;
//    private String email;
//    private String encodedImage;

//    private static final int CAMERA_REQUEST = 1888;
//    private byte[] b;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//        Button mSignUp = (Button) findViewById(R.id.signUp);
//        TextView mTv_login = (TextView) findViewById(R.id.tv_login);
//        et_name = (EditText) findViewById(R.id.et_name);
//        et_email = (EditText) findViewById(R.id.et_email);
//        img_profile = (ImageView) findViewById(R.id.img_profile);
//        et_password = (EditText) findViewById(R.id.et_password);
//        et_cpassword = (EditText) findViewById(R.id.et_cpassword);
//        et_contact = (EditText) findViewById(R.id.et_contact);
//        getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
//        );
//
//        //get realm instance
//        this.realm = RealmController.with(this).getRealm();
//
//        mSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                email = et_email.getText().toString().trim();
//                Submited();
//
//            }
//        });
//
//        mTv_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(i);
//            }
//        });
//
//        img_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectImage();
//            }
//        });
//    }
//
//    private void Submited() {
//
//        if (et_name.getText().toString().trim().length() == 0) {
//            et_name.setError("Name is not entered");
//            et_name.requestFocus();
//        }
//        if (et_email.getText().toString().trim().length() == 0) {
//            et_email.setError("Email is not entered");
//            et_email.requestFocus();
//        }
//        if (et_password.getText().toString().trim().length() == 0) {
//            et_password.setError("Password is not entered");
//            et_password.requestFocus();
//        }
//        if (et_cpassword.getText().toString().trim().length() == 0) {
//            et_cpassword.setError("Confirm Password is not entered");
//            et_cpassword.requestFocus();
//        }
//        if (et_contact.getText().toString().trim().length() == 0) {
//            et_contact.setError("Contact no. is not entered");
//            et_contact.requestFocus();
//        } else {
//            String mEmailPattern = "[a-zA-Z0-9._-]+@[gmail]+.[com]+";
//            if (email.matches(mEmailPattern)) {
//                if (et_password.getText().toString().trim().equals(et_cpassword.getText().toString().trim())) {
//                    if (!AJUtils.isNetworkAvailable(this)) {
//                        AJUtils.showToast(getResources().getString(R.string.no_internet), this);
//                        if (registerInDb()) {
//                            Registration registrations = realm.where(Registration.class).equalTo("email", et_email.getText().toString().trim()).equalTo("password", et_password.getText().toString().trim()).findFirst();
//                            if (registrations == null) {
//                                Toast.makeText(getApplicationContext(), "Worng email password", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Session session = new Session();
//                                session.setEmail(et_email.getText().toString().trim());
//                                session.setName(et_name.getText().toString().trim());
//                                session.setContact(et_contact.getText().toString().trim());
//                                session.setPassword(et_password.getText().toString().trim());
//                                session.setMphoto(b);
//                                session.setNetwork("offline");
//                                session.setStatus("LoggedIn");
//
//                                //Add to db
//                                realm.beginTransaction();
//                                realm.copyToRealm(session);
//                                realm.commitTransaction();
//
//                                Intent it = new Intent(getApplicationContext(), DeviceListActivity.class);
//                                startActivity(it);
//                            }
//
//                        }
//                    } else {
//                        Signup();
//                    }
//
//                } else {
//                    et_cpassword.setError("Confirm password does not match");
//                    et_cpassword.requestFocus();
//                    Toast.makeText(getApplicationContext(), "Confirm password does not match", Toast.LENGTH_SHORT).show();
//                }
//
//            } else {
//                et_email.setError("Invalid email address");
//                Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
//
//            }
//        }
//    }
//
//    boolean registerInDb() {
//        boolean check;
//        Registration registration = new Registration();
//        Registration registration2 = realm.where(Registration.class).equalTo("email", et_email.getText().toString().trim()).findFirst();
//
//        if (registration2 == null) {
//            check = true;
//            registration.setEmail(et_email.getText().toString().trim());
//            registration.setName(et_name.getText().toString().trim());
//            registration.setPassword(et_password.getText().toString().trim());
//            registration.setContact(et_contact.getText().toString().trim());
//            registration.setMphoto(b);
//
//            //Add to db
//            realm.beginTransaction();
//            realm.copyToRealm(registration);
//            realm.commitTransaction();
//        } else {
//            check = false;
//            Toast.makeText(getApplicationContext(), "Email already exist", Toast.LENGTH_SHORT).show();
//        }
//        return check;
//    }
//
//
//    //Signup if have internet connection
//    private void Signup() {
//
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put(Const.URL, Const.ServiceType.REGISTER);
//        map.put(Const.Params.EMAIL, et_email.getText().toString());
//        map.put(Const.Params.PASSWORD, et_password.getText().toString());
//        map.put(Const.Params.MOBILE, et_contact.getText().toString());
//        map.put(Const.Params.NAME, et_name.getText().toString());
//        map.put(Const.Params.PROFILE_NAME, encodedImage);
//        map.put(Const.Params.USER_TYPE, "0");
//        new HttpRequester(this, map, Const.ServiceCode.REGISTER, this);
//
//    }
//
//
//    @Override
//    public void onTaskCompleted(String response, int serviceCode) throws JSONException {
//        Log.d(this.getClass().getName(), "response RegisterActivity" + response);
//        switch (serviceCode) {
//            case Const.ServiceCode.REGISTER:
//                AJUtils.removeCustomProgressRequestDialog();
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String status = jsonObject.getString("status");
//
//                    if (status.equals("1")) {
//                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//
//                        String name = jsonObject1.getString("name");
//                        String phone_number = jsonObject1.getString("phone_number");
//                        String email = jsonObject1.getString("email");
//                        String profile_img = jsonObject1.getString("profile_img");
//
//                        Session session = new Session();
//                        session.setEmail(et_email.getText().toString().trim());
//                        session.setName(name);
//                        session.setContact(phone_number);
//
//                        session.setEmail(email);
//                        session.setProfile_img(profile_img);
//                        session.setPassword(et_password.getText().toString().trim());
//                        session.setNetwork("online");
//                        session.setStatus("LoggedIn");
//
//                        //Add to db
//                        realm.beginTransaction();
//                        realm.copyToRealm(session);
//                        realm.commitTransaction();
//
//                        Intent i = new Intent(getApplicationContext(), DeviceListActivity.class);
//                        startActivity(i);
//                        finish();
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Email already registered", Toast.LENGTH_SHORT).show();
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                break;
//        }
//
//    }
//
//    private void selectImage() {
//
//        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//        builder.setTitle("Add Photo!");
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (options[item].equals("Take Photo")) {
//                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    try {
//                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
//                    } catch (SecurityException e) {
//                        e.printStackTrace();
//                    }
//
//                } else if (options[item].equals("Choose from Gallery")) {
//                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent, 2);
//
//                } else if (options[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Bitmap mMphoto;
//        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {//from camera
//
//            mMphoto = (Bitmap) data.getExtras().get("data");
//            System.out.println("bitmappp" + mMphoto);
//            img_profile.setImageBitmap(mMphoto);
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            mMphoto.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//            b = byteArrayOutputStream.toByteArray();
//            encodedImage = null;
//            try {
//                System.gc();
//                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
//            } catch (Exception e) {
//                e.printStackTrace();
//            } catch (OutOfMemoryError e) {
//                byteArrayOutputStream = new ByteArrayOutputStream();
//                mMphoto.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
//                b = byteArrayOutputStream.toByteArray();
//                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
//            }
//
//        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {//from gallery
//            try {
//                Uri selectedImageUri = data.getData();
//                System.out.println("imagedata" + data);
//                img_profile.setImageURI(selectedImageUri);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            Bitmap bitmap = ((BitmapDrawable) img_profile.getDrawable()).getBitmap();
//            mMphoto = bitmap;
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
//            b = byteArrayOutputStream.toByteArray();
//            encodedImage = null;
//            try {
//                System.gc();
//                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
//            } catch (Exception e) {
//                e.printStackTrace();
//            } catch (OutOfMemoryError e) {
//                byteArrayOutputStream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
//                b = byteArrayOutputStream.toByteArray();
//                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
//            }
//
//        }
//    }
//}
