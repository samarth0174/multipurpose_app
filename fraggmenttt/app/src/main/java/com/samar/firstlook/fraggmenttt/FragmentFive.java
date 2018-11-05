package com.samar.firstlook.fraggmenttt;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.widget.ShareDialog;
import com.parse.ParseFacebookUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FragmentFive extends Fragment {

    private static final String TAG = FragmentFive.class.getSimpleName();
    ImageView profileImageView;
    TextView profileInfoTextView;
    CallbackManager callbackManager;
    FacebookCallback<LoginResult> facebookCallback;
    ProfileTracker profileTracker;
    Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getApplicationContext());
      //  LoginManager.getInstance().logOut();

        //Initialize the CallbackManager using the
        // CallbackManager.Factory.create() method to
        // create a new instance of it.
        callbackManager = CallbackManager.Factory.create();

        View v = inflater.inflate(R.layout.fragment_five, container, false);


        profileImageView = (ImageView) v.findViewById(R.id.profile_image);
        profileInfoTextView = (TextView) v.findViewById(R.id.profile_info_textview);

        //Initialize the FacebookCallback and then override its methods
        // for performing actions.
        //facebookCallback = new FacebookCallback<LoginResult>()
        final LoginButton loginButton = (LoginButton) v.findViewById(R.id.login_button);
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager,new FacebookCallback<LoginResult>(){
            @Override
            public void onSuccess(LoginResult loginResult) {
                //This code will be performed when a user is
                // successfully logged in.
            //    loginButton.setVisibility(View.INVISIBLE);
                Log.d(TAG, "FacebookCallback was Successful");
                profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                        //Whenever the user profile is changed,
                        //this method will be called.
                        if (newProfile == null) {
                           // LoginManager.getInstance().logOut();
                            profileImageView.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                            profileInfoTextView.setText("");
                        }else {

                            setUpImageAndInfo(newProfile);

                        }


                    }
                };

                profileTracker.startTracking();

            }

            @Override
            public void onCancel() {
                //This code will be performed when a user
                // login is cancelled.
                Log.d(TAG, "FacebookCallback Cancelled");
                LoginManager.getInstance().logOut();
            }

            @Override
            public void onError(FacebookException e) {
                //This code will be performed when a users login
                // attempt gets errors.
                Log.d(TAG, "FacebookCallback had Errors with n" + e);
            }
        });

        //Initialize loginButton and register the
        // CallbackManager and FacebookCallback

//        loginButton.registerCallback(callbackManager,facebookCallback);

        //Initialize the ProfileTracker and override its
        // onCurrentProfileChanged(...) method.
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                //Whenever the user profile is changed,
                //this method will be called.
                if (newProfile == null) {
                    profileImageView.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                    profileInfoTextView.setText("");
                }else {

                    setUpImageAndInfo(newProfile);

                }


            }
        };

       profileTracker.startTracking();




        return v;
    }


    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
// add your code here which executes after the execution of onCreateView() method.
        AppEventsLogger.activateApp(getApplicationContext());
        Profile userProfile = Profile.getCurrentProfile();
        if (userProfile != null) {
            setUpImageAndInfo(userProfile);
        }
    }

@Override
public void onResume() {
    super.onResume();
    // Call the 'activateApp' method to log an app event for use in analytics and advertising reporting.
 //   AppEventsLogger.activateApp(getApplicationContext());
    Profile userProfile = Profile.getCurrentProfile();
    if (userProfile != null) {
        setUpImageAndInfo(userProfile);
    }
}



    @Override
    public void onStop() {
        super.onStop();
        LoginManager.getInstance().logOut();
        profileTracker.stopTracking();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //When the login  button is clicked and user logs in.
        // After that, onActivityResult method is called
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    public void setUpImageAndInfo(Profile userProfile) {
        //This method will fill up the ImageView and TextView
        // that we initialized before.
        final String userInfo = "<u>First Name:</u> " + userProfile.getFirstName() +
                "<br/><u>Last Name:</u> " + userProfile.getLastName() +
                "<br/><u>User Id:</u> " + userProfile.getId();
        profileInfoTextView.setText(Html.fromHtml(userInfo));

        //I am using the Picasso library to download the image
        // from URL and then load it to the ImageView.
        Glide.with(getContext())
                .load("https://graph.facebook.com/" + userProfile.getId().toString() + "/picture?type=large")
                .into(profileImageView);
    }
}

















