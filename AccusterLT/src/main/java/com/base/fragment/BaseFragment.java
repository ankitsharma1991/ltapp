package com.base.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;


import com.base.utility.ToastUtils;
import com.accusterltapp.R;
import com.accusterltapp.activity.Splash;

import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseFragment extends Fragment {
    private ProgressDialog mProgressDialog;


    public  void init(View mRootView){}

    public  void setViewData(){}

    //    setFragment in container and add to stack
    public void setFragment(Fragment fragment, String fragmentNameTag) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.right_out);
        fragmentTransaction.replace(R.id.frame, fragment, fragmentNameTag);
        fragmentTransaction.addToBackStack(fragmentNameTag);
        fragmentTransaction.commit();
    }


    //    setFragment in container and add to stack
    public void addFragment(Fragment fragment, String fragmentNameTag) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.right_out);
        fragmentTransaction.add(R.id.frame, fragment, fragmentNameTag);
        fragmentTransaction.addToBackStack(fragmentNameTag);
        fragmentTransaction.commit();
    }


    public void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public void showDialogFragment(AppCompatDialogFragment dialogFragment, String tag) {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(tag);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment.show(ft, tag);
    }

    public void onClickNext() {

    }

    public void onClickPrevious() {

    }

    public void showVirturalKeyboard() {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (m != null) {
                    m.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        }, 100);
    }



    public void hideVirturalKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null&&getActivity().getWindow().getCurrentFocus()!=null) {
            imm.hideSoftInputFromWindow(getActivity().getWindow().getCurrentFocus().getWindowToken(), 0);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (Splash.btSocket == null || !Splash.btSocket.isConnected()) {
           // ToastUtils.showShortToastMessage(getActivity(),"Bluetooth disconnected");
        }
    }
}
