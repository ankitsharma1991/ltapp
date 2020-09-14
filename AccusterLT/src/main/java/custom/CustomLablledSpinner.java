package custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.accusterltapp.R;


/**
 */
public class CustomLablledSpinner extends LinearLayout implements AdapterView.OnItemSelectedListener {

    public TextView mLabel, tvError;
    public Spinner spinner;
    public View lineView;
    CustomSpinnerItemListener customSpinnerItemListener;

    public CustomLablledSpinner(Context context) {
        super(context);
        init(context);
    }

    public CustomLablledSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomLablledSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomLablledSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {

        View mRootView = LayoutInflater.from(context).inflate(
                R.layout.widget_labelled_spinner, null);

        mLabel = mRootView.findViewById(R.id.mlabel);
        tvError = mRootView.findViewById(R.id.tv_error);
        lineView = mRootView.findViewById(R.id.line_view);
        spinner = mRootView.findViewById(R.id.spinner_view);
        spinner.setOnItemSelectedListener(this);
        addView(mRootView);
    }

    public void setHint(String hint) {
        if (hint != null) { mLabel.setText(hint); }
    }

    public void hideHint(boolean isShowHint) {
        if (isShowHint) { mLabel.setVisibility(View.GONE); } else {
            mLabel.setVisibility(View.VISIBLE);
        }
    }

    public void setError(String error) {
        if (error != null) {
            tvError.setVisibility(VISIBLE);
            tvError.setText(error);
            spinner.requestFocusFromTouch();
        } else {
            tvError.setError("");
            tvError.setVisibility(GONE);
        }
    }


    public void setSpinnerEnable(boolean mIsEnable) {
        spinner.setEnabled(mIsEnable);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        tvError.setText(null);
        customSpinnerItemListener.onItemSelection(spinner, position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setClickListener(CustomSpinnerItemListener customSpinnerItemListener) {
        this.customSpinnerItemListener = customSpinnerItemListener;
    }

    public void setAdapter(CustomSpinnerAdapter customSpinnerAdapter) {
        spinner.setAdapter(customSpinnerAdapter);
    }

    public void setSelection(int position) {
        spinner.setSelection(position);
    }

    public void spinnerPosition(int itemPos) {
        spinner.setSelection(itemPos);
    }
}
