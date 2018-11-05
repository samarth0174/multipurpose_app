package com.samar.firstlook.fraggmenttt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder>{
    ArrayList<String> listOfAllImages= new ArrayList<String>();
    Context context;
    public PhotoAdapter(Context context, ArrayList  listOfAllImages) {
        this.context = context;
        this.listOfAllImages= listOfAllImages;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

//    @Override
//    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
//        holder.image.setImageResource((Integer) pix.get(position));
//    }

    @Override
    public void onBindViewHolder(PhotoAdapter.MyViewHolder holder, final int position){
        final String  data = listOfAllImages.get(position);
        if(data!=null)
        {
            Glide.with(context).load(data).into(holder.image);
            }
        else {
            Toast.makeText(context, "Images Empty", Toast.LENGTH_SHORT).show();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
              //  Intent intent = new Intent(context, GallerySecond.class);
//                intent.putParcelableArrayListExtra("data", data);
               // intent.putExtra("image", data); // put image data in Intent

            //    context.startActivity(intent); // start Intent

                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putStringArrayList("imageURLs", listOfAllImages);
                Intent intent = new Intent(context, GallerySecond.class);
                intent.putExtras(bundle);
                context.startActivity(intent);



            }
        });


    }


    @Override
    public int getItemCount() {
        return  listOfAllImages.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        // TextView name;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            //   name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);

        }
    }
}
