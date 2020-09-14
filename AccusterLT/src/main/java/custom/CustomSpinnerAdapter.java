package custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.google.gson.Gson;
import com.accusterltapp.R;

import java.util.List;

/**
 */
public class CustomSpinnerAdapter<T> extends ArrayAdapter<T> {
    private Context context;
    private List<T> values, hint_value;
    private LayoutInflater inflater;
    private boolean isError;
    int res_id;
    private boolean showDefaultText = true;

    public CustomSpinnerAdapter(Context context, int textViewResourceId, List<T> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
        this.hint_value = values;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<T> values) {
        if (values != null) {
            this.values.addAll(values);
        }
    }

    public void clearAllWithoutHint() {
        values.clear();
        this.values = hint_value;
        notifyDataSetChanged();
    }

    public int getCount() {
        if (values == null) {
            return 0;
        }
        return values.size();
    }

    public T getItem(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spiner_item_view, parent, false);
        }
        TextView label = convertView.findViewById(R.id.item_name);
        if (position == 0 && showDefaultText) {
            if (!isError) {
                label.setHintTextColor(context.getResources().getColor(R.color.hintscolor));
            } else {
                label.setHintTextColor(context.getResources().getColor(R.color.colorblack));
            }
            label.setHint(values.toArray(new Object[values.size()])[position]
                    .toString());

        } else {
            try {
                label.setText(values.toArray(new Object[values.size()])[position]
                        .toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = inflater.inflate(R.layout.drop_spiner_item_view, parent,
                false);
        TextView label = row.findViewById(R.id.item_name);
        if (position != 0) {
            label.setText(values.toArray(new Object[values.size()])[position]
                    .toString());
        } else if (!showDefaultText) {
            label.setText(values.toArray(new Object[values.size()])[position]
                    .toString());
        } else {
            label.setHint(values.toArray(new Object[values.size()])[position]
                    .toString());
        }
        return row;
    }

    public void setError() {
        isError = true;
        notifyDataSetChanged();
    }

    public void setNormal() {
        isError = false;
        notifyDataSetChanged();
    }

    public void setShowDefaultText(boolean showDefaultText) {
        this.showDefaultText = showDefaultText;
    }
}