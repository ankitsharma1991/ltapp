package com.accusterltapp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.accusterltapp.R;


public class PdfViwerFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_pdf_viewer,container,false);

        Uri uri=Uri.parse("http://eaccuster.com/partner/maf/get_pdf.php?id=R77ZWHRFYJ6024.pdf");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent,42);
//        String pdfUrl = "http://eaccuster.com/partner/maf/get_pdf.php?id=R77ZWHRFYJ6024"; //
//        String url = "http://docs.google.com/gview?embedded=true&url=" + pdfUrl;

//        WebView webView = view.findViewById(R.id.webview);
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl(url);

        return view;
    }
}
