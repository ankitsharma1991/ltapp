package com.accusterltapp.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.fragment.BaseDialogFragment;
import com.accusterltapp.R;

/**
 * Created by pbadmin on 22/1/17.
 */

public class GetCampDetails extends BaseDialogFragment implements View.OnClickListener {
    private AppCompatButton mButtonOk;
    private AppCompatEditText mEditTextEmailId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.get_camp_details, container, false);
        init(mRootView);
        return mRootView;
    }

    private void init(View mRootView) {

        mButtonOk = mRootView.findViewById(R.id.btn_ok);
        mButtonOk.setOnClickListener(this);
        AppCompatButton buttonCancel = mRootView.findViewById(R.id.btn_cancel);
        buttonCancel.setOnClickListener(this);
        mRootView.findViewById(R.id.btn_create_camp).setOnClickListener(this);

        mEditTextEmailId = mRootView.findViewById(R.id.email_id);
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonOk) {
            if (validate()) {
// TODO: 5/12/17  get camp details
            }
        } else if (v == mButtonOk) {

        } else {
            dismiss();
        }


    }

    private boolean validate() {
        return true;
    }

}
