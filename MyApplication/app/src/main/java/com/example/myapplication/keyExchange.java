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

public class keyExchange extends AppCompatActivity {

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    TextView Info;

    String session_id,private_key,public_key;

    String option,responded_status;


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
        setContentView(R.layout.activity_panel);

        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // update TextView here!
//                                updateTextView();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();

        Info = (TextView) findViewById(R.id.AES_key);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            session_id = extras.getString("session_id");
            public_key = extras.getString("public_key");
            private_key = extras.getString("private_key");
        }


        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);



    }

//    private void ChattManagement(String option, String session_id, String email, String responded_status) {
//
//        compositeDisposable.add(myAPI.ChatManagemnt(option,session_id,email,"",responded_status)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        if(s.contains("sent"))
//                            Toast.makeText(keyExchange.this, "" + s, Toast.LENGTH_SHORT).show();
//
//                        else
//                            Toast.makeText(keyExchange.this, "" + s, Toast.LENGTH_SHORT).show();
//                    }
//                }));
//    }
//
//    private void updateTextView() {
//        compositeDisposable.add(myAPI.ChatManagemnt("chk_res","\"\"","asd556717@gmail.com","","")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        Info.setText(s);
//                    }
//                }));
//
//    }

}