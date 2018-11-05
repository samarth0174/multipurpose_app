package com.samar.firstlook.fraggmenttt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageFragment extends Fragment {


    private String imageURL;
    public ImageFragment() {

        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //inside onCreateView
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ImageView imageView = view.findViewById(R.id.imageView);
         imageURL = getArguments().getString("imageURL");

//         Glide.with(getActivity())
//                .load(imageURL)
//                .into(imageView);


        Bitmap bitmap = BitmapFactory.decodeFile(imageURL);
        imageView.setImageBitmap(bitmap);

        PhotoViewAttacher photoAttacher;
        photoAttacher= new PhotoViewAttacher(imageView);
        photoAttacher.update();

        return view;

    }
}