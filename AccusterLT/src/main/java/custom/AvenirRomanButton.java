package custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.accusterltapp.R;


/**
 * Created by appideas-user2 on 5/7/17.
 */

public class AvenirRomanButton extends android.support.v7.widget.AppCompatButton {

    private Typeface typeface;

    public AvenirRomanButton(Context context) {
        super(context);
    }

    public AvenirRomanButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public AvenirRomanButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.app);
        String customFont = a.getString(R.styleable.app_customFont);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    private boolean setCustomFont(Context ctx, String asset) {
        try {
            if (typeface == null) {
                // Log.i(TAG, "asset:: " + "fonts/" + asset);

                typeface = Typeface.createFromAsset(ctx.getAssets(),
                        "fonts/AvenirLTStd-Roman.otf");
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Log.e(TAG, "Could not get typeface: " + e.getMessage());
            return false;
        }

        setTypeface(typeface);
        return true;
    }
}
