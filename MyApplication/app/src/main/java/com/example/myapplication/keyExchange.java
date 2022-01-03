package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import io.reactivex.disposables.CompositeDisposable;

public class keyExchange extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    TextView Info;

    String email,private_key,secretKey;


    public keyExchange() {
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_exchange);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            secretKey = extras.getString("secret_key");
            email = extras.getString("email");
        }

        Info = (TextView) findViewById(R.id.Info);


        Info.setText(secretKey);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);


        EditText editText = findViewById(R.id.editText);

        findViewById(R.id.enterBtn)
                .setOnClickListener(v -> {

                    Intent intent = new Intent(this, ChatActivity.class);
                    intent.putExtra("name", editText.getText().toString());
                    intent.putExtra("secret_key", secretKey);
                    intent.putExtra("private_key", private_key);
                    startActivity(intent);

                });

    }

}