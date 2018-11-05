package com.samar.firstlook.fraggmenttt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.InputStream;

public class ShowDataActivity extends AppCompatActivity {


//
//    private ProfilePictureView profilePictureView;
//    private LinearLayout infoLayout;
//    private TextView email;
//    private TextView gender;
//    private TextView facebookName;

    private Button logoutbutton;
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        Bundle inBundle = getIntent().getExtras();
        String name = inBundle.get("name").toString();
        String surname = inBundle.get("surname").toString();
        String imageUrl = inBundle.get("imageUrl").toString();

        TextView nameView = (TextView) findViewById(R.id.name);
        nameView.setText("" + name + " " + surname);

       new DownloadImage((ImageView)findViewById(R.id.profileImage)).execute(imageUrl);
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent login = new Intent(ShowDataActivity.this, FragmentFive.class);
                startActivity(login);
                finish();
            }
        });
    }




        public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

            public DownloadImage(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
            }
        }



}

