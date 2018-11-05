package com.samar.firstlook.fraggmenttt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class FragmentTwo extends Fragment {
    //TextView text2;
    WebView mWebView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_two, container, false);


        mWebView=(WebView)v.findViewById(R.id.webapp);
        mWebView.loadUrl("file:///android_asset/webview.html");

//        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
// Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());


        //  text2=(TextView)v.findViewById(R.id.textView2);
    return v;
    }
}