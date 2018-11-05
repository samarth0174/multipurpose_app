package com.samar.firstlook.fraggmenttt.showforms;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.samar.firstlook.fraggmenttt.Model.User;
import com.samar.firstlook.fraggmenttt.R;

import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.UserViewHolder> {
    Context context;
    private List<User> listUsers;

    public AdapterClass(List<User> listUsers) {
        this.listUsers = listUsers;
        this.context=context;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_form_item,parent,false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewFirstName.setText(listUsers.get(position).getFirstname());
        holder.textViewLastName.setText(listUsers.get(position).getLastname());
        holder.textViewPhone.setText(listUsers.get(position).getPhone_no());
        holder.textViewDob.setText(listUsers.get(position).getDob());
        holder.textViewEmail.setText(listUsers.get(position).getEmail());
        holder.textViewPan.setText(listUsers.get(position).getPan());




        final String  data = listUsers.get(position).getImage();
        if(data!=null)
        {   holder.imguri.setText(data);
            Bitmap bitmap = BitmapFactory.decodeFile(data);
            holder.userimage.setImageBitmap(bitmap);
        }


    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public class UserViewHolder extends  RecyclerView.ViewHolder {

        public AppCompatTextView textViewFirstName;
        public AppCompatTextView textViewLastName;
        public AppCompatTextView textViewDob;
        public AppCompatTextView textViewPhone;
        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewPan;
        public AppCompatImageView userimage;
        public AppCompatTextView imguri;
        public UserViewHolder(View itemView) {
            super(itemView);

            textViewFirstName = (AppCompatTextView) itemView.findViewById(R.id.textViewFirstName);
            textViewLastName= (AppCompatTextView) itemView.findViewById(R.id.textViewLastname);
            textViewDob=(AppCompatTextView)itemView.findViewById(R.id.editTextDob);
            textViewEmail=(AppCompatTextView)itemView.findViewById(R.id.editTextEmail);
            textViewPhone=(AppCompatTextView)itemView.findViewById(R.id.editTextMobile);
            textViewPan=(AppCompatTextView)itemView.findViewById(R.id.panno);
           userimage=(AppCompatImageView)itemView.findViewById(R.id.userimage);
          imguri=(AppCompatTextView)itemView.findViewById(R.id.uri);


        }
    }
}


