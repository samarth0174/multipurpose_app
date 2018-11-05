package com.samar.firstlook.fraggmenttt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class FragmentSix extends Fragment {
   Button maps;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_six, container, false);

        maps=(Button)v.findViewById(R.id.mapbutton);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapintent=new Intent(getActivity(),MapsActivity.class);
                startActivity(mapintent);
            }
        });

        return v;
    }
}