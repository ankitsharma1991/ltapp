package com.base.fragment;

import android.support.v7.app.AppCompatDialogFragment;

import com.base.listener.DialogCloseListener;

/**
 * Created by pbadmin on 22/1/17.
 */

public class BaseDialogFragment extends AppCompatDialogFragment {
    private DialogCloseListener dialogListener;


    public void setDialogListener(DialogCloseListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public DialogCloseListener getDialogListener() {
        return dialogListener;
    }
}
