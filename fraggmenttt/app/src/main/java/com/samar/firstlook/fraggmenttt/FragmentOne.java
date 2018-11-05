package com.samar.firstlook.fraggmenttt;

import android.Manifest;
import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.content.ContentResolver;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentOne extends Fragment {


    public FragmentOne() {
        // Required empty public constructor
    }

    RecyclerView rvContacts;
    EditText editTextSearch;
    ArrayList<String> names;

    Contact_adapter adapter;
    //public static final int RequestPermissionCode = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        View root = inflater.inflate(
                R.layout.fragment_one, container, false);


        rvContacts = (RecyclerView) root.findViewById(R.id.rvContacts);

        bindContactView();

        // getAllContacts();



        return root;


    }


    public void bindContactView() {

        new MyAsyncTask().execute();
    }


    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        List<ContactVO> TempList=new ArrayList<>();
        //ContactVO contactVO;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            TempList=getAllContacts(getActivity());
          //  TempList = Arrays.asList(here);
          //  Log.e("contactData: ", String.valueOf(TempList.size()));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            rvContacts.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvContacts.setAdapter(new Contact_adapter(TempList, getActivity()));
        }
    }


//slow contact extraction method
//
//
//    private List<ContactVO> getAllContacts(Activity activity) {
//        List<ContactVO> contactVOList = new ArrayList();
//        ContactVO contactVO;
//
//        ContentResolver contentResolver = getActivity().getContentResolver();
//        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//
//                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
//                if (hasPhoneNumber > 0) {
//                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//
//                    contactVO = new ContactVO();
//                    contactVO.setContactName(name);
//
//                    Cursor phoneCursor = contentResolver.query(
//                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                            new String[]{id},
//                            null);
//                    if (phoneCursor.moveToNext()) {
//                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        contactVO.setContactNumber(phoneNumber);
//                    }
//
//                    phoneCursor.close();
//
//                    contactVOList.add(contactVO);
//                }
//            }
//
////            Contact_adapter contactAdapter = new Contact_adapter(contactVOList, getActivity().getApplicationContext());
////            rvContacts.setLayoutManager(new LinearLayoutManager(getActivity()));
////            rvContacts.setAdapter(contactAdapter);
//        }
//
//        return  contactVOList;
//
//  }
//
////fast method by using only a single cursor

    public ArrayList<ContactVO> getAllContacts(Activity activity) {
        Log.d("START","Getting all Contacts");
        ArrayList<ContactVO> arrContacts = new ArrayList<ContactVO>();
        ContactVO contactVO;
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = contentResolver.query(uri,null,null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
                //new String[] {ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone._ID}, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
       // cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
           // if (hasPhoneNumber > 0) {
                contactVO = new ContactVO();
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
          //  int phoneContactID = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
            //phoneContactInfo.setPhoneContactID(phoneContactID);
            contactVO.setContactName(contactName);

               // if (Boolean.parseBoolean(String.valueOf(hasPhoneNumber))) {
                    if (cursor.moveToNext()) {
                        String contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactVO.setContactNumber(contactNumber);
                    }
                //}
           // if (contactVO != null) {
                arrContacts.add(contactVO);
            //}
            // contactVO = null;
           // cursor.moveToNext();
        }

        cursor.close();
        cursor = null;
        Log.d("END","Got all Contacts");
        return arrContacts;
    }

}