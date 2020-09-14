package com.accusterltapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accusterltapp.model.Heleprec;
import com.accusterltapp.model.SubTestDetails;
import com.accusterltapp.service.ImageLodingService;
import com.base.listener.RecyclerViewListener;
import com.base.utility.StringUtils;
import com.base.utility.ToastUtils;
import com.accusterltapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import ss.com.bannerslider.Slider;

/**
 * Created by pbadmin on 3/11/17.
 */
public class PatientTestListAdapter extends RecyclerView.Adapter<PatientTestListAdapter.CardViewHolder> {

    private ArrayList<SubTestDetails> patientTestList;
    private Context mContext;
    private RecyclerViewListener mListener;


    public PatientTestListAdapter(Context context, ArrayList<SubTestDetails> itemList, RecyclerViewListener listener) {
        Log.e("item list",itemList.size()+"jkj.s");
        mContext = context;
        patientTestList = itemList;
        mListener = listener;

    }

    @Override
    public PatientTestListAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_details, parent, false);
        return new PatientTestListAdapter.CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {
        final SubTestDetails patient = patientTestList.get(position);
     //
        //        holder.testName.setText(patient.getpPatientId());
        holder.testName.setText("Name:- " + patient.getTest_name());
        holder.cb.setOnCheckedChangeListener(null);
        if (patient.isSelected())
        holder.cb.setChecked(true);
else
        holder.cb.setChecked(false);
holder.mainLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(holder.cb.isChecked())
        {
            holder.cb.setChecked(true);
            patientTestList.get(position).setSelected(true);
        }
        else {
            holder.cb.setChecked(false);
            patientTestList.get(position).setSelected(false);
        }
    }
});
        holder.testAmount.setText("Amount " + mContext.getString(R.string.Rs) + StringUtils.doubleToIndianFormat(patient.getTest_price()));
        holder.testCode.setText("Code:- " + patient.getTest_code());
        if (patient.is_manual_status() && TextUtils.isEmpty(patient.getTest_result())) {
            holder.mLayoutResult.setVisibility(View.VISIBLE);
            holder.mLinearLayoutEdit.setVisibility(View.GONE);
           // holder.pri_layout.setVisibility(View.GONE);
        } else {
           holder.mLayoutResult.setVisibility(View.GONE);
            //holder.pri_layout.setVisibility(View.VISIBLE);
            holder.mLinearLayoutEdit.setVisibility(View.VISIBLE);
            holder.mViewEdit.setVisibility(patient.is_manual_status() ? View.VISIBLE : View.GONE);
            holder.pri.setVisibility(patient.isImagepre() ? View.VISIBLE : View.GONE);
            holder.pri.setVisibility(patient.isImg_pri()? View.VISIBLE : View.GONE );
        }
        holder.pri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (patient.isImagepre()) {
                    final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar);
                    dialog.setContentView(R.layout.rapid_image_d);
                    Slider slider=dialog.findViewById(R.id.banner_slider1);
                    Slider.init(new ImageLodingService(mContext));
                    Log.e("imagePath adapter ", patientTestList.get(position).getImagePath() + " ");
                    //  Bitmap  b=Bitmap.createBitmap("/storage/emulated/0/report_image/"+patient.getImagePath()+".jpg")
                 //   Glide.with(mContext).load("/storage/emulated/0/report_image/" + patientTestList.get(position).getImagePath() + ".jpg").into(im);
                   /// Bitmap myBitmap = BitmapFactory.decodeFile("/storage/emulated/0/rapid/" + patientTestList.get(position).getImagePath() + ".jpg");
                  //  ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);
                    Gson gson=new Gson();
                    ArrayList<String> imageName = gson.fromJson( patientTestList.get(position).getImagePath(), ArrayList.class);
                   // Log.e("size and data",imageName+" and "+imageName.size());
                    if (imageName!=null) {
                        slider.setAdapter(new SliderAddapterc(mContext, imageName));
                        dialog.show();
                    }
                    else  Toast.makeText(mContext,"sorry image not present",Toast.LENGTH_LONG).show();
                   // Log.e("imagePath adapter ", patientTestList.get(position).getImagePath() + " ");
                }
                else {
                    Toast.makeText(mContext,"sorry image not present",Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.testResult.setText("Result:- " + patient.getTest_result());

        holder.mViewDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(holder.mEditTextResult.getText().toString())) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("TestName", patient.getTest_name());
                    hashMap.put("result", holder.mEditTextResult.getText().toString());
                    Log.e("data is ",patientTestList.get(position).getImage_permission()+" data");
                    hashMap.put("image_permission",patientTestList.get(position).getImage_permission());
                    mListener.onItemClickListener(v, position, hashMap);
                } else {
                    ToastUtils.showShortToastMessage(mContext, "enter test result");
                }
            }
        });
        holder.mViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mLayoutResult.setVisibility(View.VISIBLE);
                holder.mLinearLayoutEdit.setVisibility(View.GONE);
            }
        });
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    patientTestList.get(position).setSelected(b);
                    Heleprec. testlist.add(patientTestList.get(position));
                }
                else {
                    patientTestList.get(position).setSelected(b);
                    Heleprec.testlist.remove(patientTestList.get(position));
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return patientTestList.size();
    }
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView testName, testAmount, testResult, testCode;
        public EditText mEditTextResult;
       public LinearLayout mainLayout;
        private LinearLayout mLayoutResult, mLinearLayoutEdit;
        private ImageView mViewDone, mViewEdit,pri;
        public CheckBox cb;
        public RelativeLayout pri_layout;
        public CardViewHolder(View itemView) {
            super(itemView);
            cb=itemView.findViewById(R.id.cb);
            mainLayout=itemView.findViewById(R.id.mainlayout);
            pri=itemView.findViewById(R.id.pri);
           // pri_layout=itemView.findViewById(R.id.pri_layout);
            testName = itemView.findViewById(R.id.testName);
            testAmount = itemView.findViewById(R.id.testAmount);
            testCode = itemView.findViewById(R.id.testCode);
            testResult = itemView.findViewById(R.id.testResult);
            mEditTextResult = itemView.findViewById(R.id.enterResult);
            mEditTextResult.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    v.onTouchEvent(event);
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                       // imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        imm.showSoftInputFromInputMethod(v.getWindowToken(),0);
                    }
                    return true;
                }
            });
            mLayoutResult = itemView.findViewById(R.id.lv_result);
            mLinearLayoutEdit = itemView.findViewById(R.id.lv_result_edit);
            mViewDone = itemView.findViewById(R.id.imvDone);
            mViewEdit = itemView.findViewById(R.id.imvEdit);
        }
    }
}