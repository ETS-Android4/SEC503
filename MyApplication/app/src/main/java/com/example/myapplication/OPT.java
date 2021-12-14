package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Retrofit.INodeJS;
import com.example.myapplication.Retrofit.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class OPT extends AppCompatActivity {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    MaterialButton submit;
    TextView otp;

    String value;



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
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             value = extras.getString("session_id");
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opt);

        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);

        submit = (MaterialButton) findViewById(R.id.submit);
        otp = (TextView) findViewById(R.id.otp);



        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                otpuser(otp.getText().toString(),value.toString());
            }
        });
    }

    private void otpuser(String otp,String session_id) {
        compositeDisposable.add(myAPI.otpuser(otp,session_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if(s.contains("Successfully"))
                            Toast.makeText(OPT.this,""+s,Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(OPT.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }
}