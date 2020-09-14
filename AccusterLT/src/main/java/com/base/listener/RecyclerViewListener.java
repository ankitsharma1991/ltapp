package com.base.listener;

import android.view.View;

import java.util.HashMap;


/**
 * Created by Vinod Ranga on 12/19/2015.
 */
public interface RecyclerViewListener {

    void onItemClickListener(View view, int position, HashMap<String, String> hashMap);
}
