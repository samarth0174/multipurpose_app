package com.samar.firstlook.fraggmenttt;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.samar.firstlook.fraggmenttt.Model.User;
import com.samar.firstlook.fraggmenttt.showforms.FormsActivity;
import com.samar.firstlook.fraggmenttt.sql.DatabaseHelper;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.IOException;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

//
public class FragmentFour extends Fragment  {
    //TextView text2;
    TextInputEditText Firstname,editTextDob;

    TextInputEditText editTextEmail,editTextMobile,pannumber;
    TextInputEditText Lastname;
    AppCompatButton buttonsubmit;
    AppCompatButton gotodatabasebutton;
    ImageView imageView;
    private DatabaseHelper databaseHelper;
    private User user;
    DatePickerDialog datePickerDialog;
    AppCompatButton datesetbutton;
    public Uri returnUri;
    AppCompatButton clearbutton;
    Bitmap bitmapImage = null;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

   // private AwesomeValidation awesomeValidation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View v = inflater.inflate(R.layout.fragment_four, container, false);

       imageView=(ImageView)v.findViewById(R.id.profile);

       imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //PickImageDialog.build(new PickSetup()).show(getActivity());
//               pickImage();

               if(ActivityCompat.checkSelfPermission(getActivity(),
                       Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
               {
                   requestPermissions(
                           new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                           2000);
               }



               else
               {
                   startGallery();
               }


           }
       });



        Firstname = (TextInputEditText) v.findViewById(R.id.firstName);
        Firstname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(Firstname);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        Lastname=(TextInputEditText)v.findViewById(R.id.lastName);
        Lastname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(Lastname);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });




        editTextDob = (TextInputEditText) v.findViewById(R.id.editTextDob);
       // editTextDob.setFocusable(false);
        editTextDob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(editTextDob);
            }
        });



        datesetbutton=(AppCompatButton)v.findViewById(R.id.setdobbutton);
        datesetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdate();
            }
        });


        editTextEmail = (TextInputEditText) v.findViewById(R.id.editTextEmail);

        editTextEmail.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(editTextEmail);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        editTextMobile = (TextInputEditText) v.findViewById(R.id.editTextMobile);

        editTextMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(editTextMobile);
            }
        });

        pannumber=(TextInputEditText)v.findViewById(R.id.panno);
        pannumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(pannumber);
            }
        });
        buttonsubmit=(AppCompatButton) v.findViewById(R.id.buttonSubmit);
        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Validation class will check the error and display the error on respective fields
                but it won't resist the form submission, so we need to check again before submit
                 */
                if ( checkValidation () )

                {
                    Addit();
                    Toast.makeText(getActivity(), "Form Submitted", Toast.LENGTH_LONG).show();
                  //  emptyInputEditText();
                   submitForm();
                    }
//

                else
                    Toast.makeText(getActivity(), "Form contains error", Toast.LENGTH_LONG).show();
            }
        });


        clearbutton=(AppCompatButton)v.findViewById(R.id.buttonClear);
        clearbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyInputEditText();
            }
        });




       gotodatabasebutton= (AppCompatButton) v.findViewById(R.id.gotodatabasebutton);
       gotodatabasebutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               submitForm();

           }
       });





        return v;
    }



    private void setdate() {

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        editTextDob.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                returnUri = data.getData();

                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmapImage,108,100,false));
            }
        }
//        else if(requestCode==RESULT_OK)
//        {
//            if(requestCode==2000)
//            {
//                returnUri=data.getData();
//
//                try {
//                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmapImage,108,100,false));
//            }
//
//            }
        }




    public void Addit()
    {
        new ReadRssBackground().execute();
    }



    public class ReadRssBackground extends AsyncTask<Context, Void, Void> {

        @Override
        protected void onPreExecute() {
            databaseHelper = new DatabaseHelper(getActivity());
            user=new User();
        }

        @Override
        protected Void doInBackground(Context... contexts) {
//            if (!databaseHelper.checkUser(Firstname.getText().toString().trim(), Lastname.getText().toString().trim(), editTextDob.getText().toString().trim(),
//                    editTextMobile.getText().toString().trim(), editTextEmail.getText().toString().trim(), pannumber.getText().toString().trim())) {

                user.setFirstname(Firstname.getText().toString().trim());
                user.setLastname(Lastname.getText().toString().trim());
                user.setDob(editTextDob.getText().toString().trim());
                user.setEmail(editTextEmail.getText().toString().trim());
                user.setPhone_no(editTextMobile.getText().toString().trim());
                user.setPan(pannumber.getText().toString().trim());
               if(returnUri!=null) {

                  String path= getRealPathFromURI(returnUri);


                  user.setImage(path.trim());

               }
                databaseHelper.AddUser(user);

            //}
//            else
//                    {
//                        Toast.makeText(getActivity(), "Duplicate Data", Toast.LENGTH_LONG).show();
//                    }

            return null;
        }
    }






    private void submitForm() {
        // Submit your form here. your form is valid

        Toast.makeText(getActivity(), "Next page...", Toast.LENGTH_LONG).show();
        Intent i=new Intent(getContext(),FormsActivity.class);
        startActivity(i);
    }


    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(Firstname)) ret = false;
        if(!Validation.hasText(Lastname))ret=false;
        if(!Validation.hasText(editTextDob))ret=false;

        if (!Validation.isEmailAddress(editTextEmail,true)) ret = false;
        if(!Validation.isPanNumber(pannumber,true))ret=false;
        if(!Validation.isPhoneNumber(editTextMobile,true))ret=false;



        return ret;
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(contentUri, proj, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        }
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    private void startGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, 1000);
        }
    }
    public void startcamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 2000);

    }




    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        Firstname.setText(null);
        Lastname.setText(null);
        editTextMobile.setText(null);
        editTextDob.setText(null);
        editTextEmail.setText(null);
        pannumber.setText(null);
        imageView.setImageBitmap(null);
    }







}
