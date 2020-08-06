package com.am.myfireotp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.ed);
    }

    public void sendotp(View view) {
        number=editText.getText().toString();
        Intent intent = new Intent(MainActivity.this,VerifyPhoneActivity.class);
        intent.putExtra("num",number);
        startActivity(intent);

    }
}