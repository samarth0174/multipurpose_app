package com.samar.firstlook.fraggmenttt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.List;


public class GallerySecond extends AppCompatActivity {
    private ViewPager viewPager;
    private ImageView imageView;
    ArrayList<String> listImageURLs;
    private int position = 0;
   // private String fullScreenInd;
    String path;
    private List<Fragment> listFragments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_second);


      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.7));

        viewPager=(ViewPager)findViewById(R.id.viewPager);

        getArguments();
        createFragments();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), listFragments);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(position);





    }



    private void getArguments() {
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            listImageURLs = bundle.getStringArrayList("imageURLs");
              position = bundle.getInt("position");
        }
    }

    private void createFragments(){
        for(int i=0;i<listImageURLs.size();i++){
            Bundle bundle = new Bundle();
            bundle.putString("imageURL", listImageURLs.get(i));
            ImageFragment imageFragment = new ImageFragment();
            imageFragment.setArguments(bundle);
            listFragments.add(imageFragment);
        }
    }



}

