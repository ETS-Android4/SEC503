package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Base64;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import com.chilkatsoft.*;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import com.example.myapplication.Retrofit.INodeJS;
import com.example.myapplication.Retrofit.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class OPT extends AppCompatActivity {

    private static final String TAG = "Chilkat";

    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MaterialButton submit;
    TextView otp;

    String value,email;
    String publ_key,pvt_key;
    Intent i;

    public OPT() throws NoSuchAlgorithmException {
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
        CkRsa rsa = new CkRsa();

        boolean success = rsa.GenerateKey(3072);
        if (success != true) {
            Log.i(TAG, rsa.lastErrorText());
            return;
        }

        publ_key = rsa.exportPublicKey();
        pvt_key = rsa.exportPrivateKey();



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             value = extras.getString("session_id");
			 email = extras.getString("email");
			 
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
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void accept(String s) throws Exception {
                        if(!s.contains("Fail")) {

                            String option ="push";
                            Toast.makeText(OPT.this, "" + s, Toast.LENGTH_SHORT).show();
                            Keymanagemnt(publ_key,session_id,option.toString());
                            i = new Intent(getApplicationContext(),panel.class);
                            i.putExtra("user_id",s);
                            i.putExtra("session_id",session_id);
                            i.putExtra("public_key",publ_key);
                            i.putExtra("private_key",pvt_key);
							i.putExtra("email",email);
                            startActivity(i);
                        }
                        else

                            Toast.makeText(OPT.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }

    private void Keymanagemnt(String pubkey, String session_id, String option) {
        compositeDisposable.add(myAPI.KeyManagemnt(option,pubkey,session_id,"")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Toast.makeText(OPT.this, "" + s, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    static {
        System.loadLibrary("chilkat");
    }
}