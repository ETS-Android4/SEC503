package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Retrofit.INodeJS;
import com.example.myapplication.Retrofit.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import io.reactivex.functions.Consumer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    MaterialButton login,signup;
    TextView email,password,name;

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
        setContentView(R.layout.activity_main);

        //Init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        //View
        login = (MaterialButton) findViewById(R.id.login);
        signup = (MaterialButton) findViewById(R.id.signup);

        email =(TextView) findViewById(R.id.email);
        password =(TextView) findViewById(R.id.password);
        name =(TextView) findViewById(R.id.name);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                loginUser(email.getText().toString(),password.getText().toString());
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                registerUser(email.getText().toString(),password.getText().toString(),name.getText().toString());
            }
        });


        //admin and admin

    }

    private void registerUser(String email, String password, String name) {
        compositeDisposable.add(myAPI.registerUser(email,name,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Log.i("Test",s);
                        Toast.makeText(MainActivity.this,""+s,Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }

    private void loginUser(String email, String password) {
        compositeDisposable.add(myAPI.loginUser(email,password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                if(s.contains("Failed") || s.contains("Exists"))
//                                Toast.makeText(context:MainActivity.this, text:""+s, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(MainActivity.this,""+s,Toast.LENGTH_SHORT).show();
                                else
                                {
                                    Intent i = new Intent(getApplicationContext(),OPT.class);
									i.putExtra("email",email);
                                    i.putExtra("session_id",s);
                                    startActivity(i);
                                }

//                                    Toast.makeText(MainActivity.this,"login Successfully",Toast.LENGTH_SHORT).show();
                            }
                        })
        );
    }
}