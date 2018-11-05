package com.samar.firstlook.fraggmenttt.showforms;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.samar.firstlook.fraggmenttt.Model.User;
import com.samar.firstlook.fraggmenttt.R;
import com.samar.firstlook.fraggmenttt.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class FormsActivity extends AppCompatActivity {


    // private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private List<User> listUsers;
    private AdapterClass usersRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms);

        initViews();
        initObjects();
    }

    private void initObjects() {
        listUsers = new ArrayList<>();
        usersRecyclerAdapter = new AdapterClass(listUsers);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);

        databaseHelper = new DatabaseHelper(FormsActivity.this);


        getDataFromSQLite();
    }

    private void initViews() {
        //   textViewName = (AppCompatTextView) findViewById(R.id.textViewFirstName);
        recyclerViewUsers = (RecyclerView) findViewById(R.id.recyclerViewUsers);

    }

    private void getDataFromSQLite() {
        new MyTask().execute();

    }




    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            listUsers.clear();
            listUsers.addAll(databaseHelper.getALLUser());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            usersRecyclerAdapter.notifyDataSetChanged();

        }
    }


}
