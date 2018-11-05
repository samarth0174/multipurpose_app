package com.samar.firstlook.fraggmenttt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.samar.firstlook.fraggmenttt.showforms.FormsActivity;

public class RandomActivity extends AppCompatActivity {

    private Button gotodatabutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

    gotodatabutton=(Button)findViewById(R.id.gotobutton);
    gotodatabutton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(RandomActivity.this, "Next page...", Toast.LENGTH_LONG).show();
            Intent i=new Intent(RandomActivity.this, FormsActivity.class);
            startActivity(i);
        }
    });


    }
}
