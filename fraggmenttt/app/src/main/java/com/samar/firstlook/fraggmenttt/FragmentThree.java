package com.samar.firstlook.fraggmenttt;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

public class FragmentThree extends Fragment {
    Boolean mExternalStorageAvailable,permission=false;
    //String[] items;//to read all files
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    PhotoAdapter adapter;
    private Context context;
    ArrayList<String> imageData;
    public  static ArrayList<String>temp;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootnode= inflater.inflate(
                R.layout.fragment_three, container, false);

        // get the reference of RecyclerView
         recyclerView = (RecyclerView) rootnode.findViewById(R.id.recyclerView);
        // set a GridLayoutManager with default vertical orientation and 2 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView


       bindGridview();


        temp= getAllShownImagesPath(getActivity());



//        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
//                new RecyclerItemClickListener.OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(View view, int position) {
//
//                        Bundle bundle = new Bundle();
//                bundle.putInt("position", position);
//                bundle.putStringArrayList("imageURLs", temp);
//                Intent intent = new Intent(context, GallerySecond.class);
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//
//
//
//                    }
//                }));




        return rootnode;
    }


    public void bindGridview() {

        new MyTask().execute();
    }


    private class MyTask extends AsyncTask<Void, Void, Void> {

         @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            imageData = getAllShownImagesPath(getActivity());
            Log.e("imageData: ", String.valueOf(imageData.size()));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView.setAdapter(new PhotoAdapter( getActivity(),imageData));

        }
    }



    private ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }





}




