package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chilkatsoft.CkRsa;
import com.example.myapplication.Retrofit.INodeJS;
import com.example.myapplication.Retrofit.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class panel extends AppCompatActivity {


    private static final String TAG = "Chilkat";
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Random rand = new Random();

    MaterialButton request,accept,reject,askpub;
    TextView data,Info;

    String session_id,private_key,public_key,user_id;
    String peer_pubkey;
	
	String email;

    String option,responded_status;
	String request_id;
    String encryptedStr;
	
	Intent i;


    public panel() {
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
                                updateTextView();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();
        request = (MaterialButton) findViewById(R.id.request);
        accept = (MaterialButton) findViewById(R.id.accept);
        reject = (MaterialButton) findViewById(R.id.reject);
        askpub = (MaterialButton) findViewById(R.id.askPub);

        data = (TextView) findViewById(R.id.email);
        Info = (TextView) findViewById(R.id.Info);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getString("user_id");
            session_id = extras.getString("session_id");
            public_key = extras.getString("public_key");
            private_key = extras.getString("private_key");
			email = extras.getString("email");
        }


        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);


        request.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                option = "req";
                responded_status = "binding";
                request_id = String.format("%04d", rand.nextInt(10000));
                ChatManagement(request_id,option,user_id,data.getText().toString(),responded_status,null);

                String key = getRandomHexString(64);

                CkRsa rsaEncryptor = new CkRsa();

                rsaEncryptor.put_EncodingMode("hex");
                boolean success = rsaEncryptor.ImportPublicKey(peer_pubkey.replaceAll("\"",""));
                boolean usePrivateKey = false;
                encryptedStr = rsaEncryptor.encryptStringENC(key,usePrivateKey);
                Log.i(TAG,encryptedStr);

				ShareKey(request_id,encryptedStr);
                Toast.makeText(panel.this,""+encryptedStr,Toast.LENGTH_SHORT).show();
				
				i = new Intent(getApplicationContext(),keyExchange.class);
				i.putExtra("secret_key",key);
				i.putExtra("private_key",private_key);
				i.putExtra("email",email);
				startActivity(i);
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option = "res";
                responded_status = "accept";

                ChatManagement(data.getText().toString(),option,"","",responded_status,null);
                compositeDisposable.add(myAPI.AskSecKey(data.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                CkRsa rsaDecryptor = new CkRsa();
                                rsaDecryptor.put_EncodingMode("hex");
                                boolean success = rsaDecryptor.ImportPrivateKey(private_key);
                                Toast.makeText(panel.this,""+private_key,Toast.LENGTH_SHORT).show();
                                boolean usePrivateKey = true;
                                Toast.makeText(panel.this,""+s.replaceAll("\"","").replaceAll("\n",""),Toast.LENGTH_SHORT).show();
                                String decryptedStr = rsaDecryptor.decryptStringENC(s.replaceAll("\"", ""),usePrivateKey);
//                                Log.i(TAG, decryptedStr);
                                Toast.makeText(panel.this,""+decryptedStr,Toast.LENGTH_SHORT).show();
                                i = new Intent(getApplicationContext(),keyExchange.class);
                                i.putExtra("secret_key",decryptedStr);
                                i.putExtra("private_Key",private_key);
                                i.putExtra("email",email);
                                startActivity(i);

                            }
                        }));

            }
        });

        askpub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Keymanagemnt("",data.getText().toString(),"ask");

            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option = "res";
                responded_status = "rejected";
                ChatManagement(data.getText().toString(),option,"","",responded_status,null);
            }
        });

    }

    private void ShareKey(String chatting_id, String dd) {
        compositeDisposable.add(myAPI.ShareKey(chatting_id,dd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(panel.this, "" + s, Toast.LENGTH_SHORT).show();
                    }
                }));
    }


    private void ChatManagement(String request_id, String option, String session_id, String email, String responded_status, String chatting_id) {

        compositeDisposable.add(myAPI.ChatManagemnt(option,request_id,session_id,email,responded_status,chatting_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(panel.this, "" + s, Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void updateTextView() {
        compositeDisposable.add(myAPI.ChatManagemnt("chk_res",null,"",email,"binding",null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Info.setText(s);
                    }
                }));

    }

    private void Keymanagemnt(String pubkey, String session_id, String option) {
        compositeDisposable.add(myAPI.KeyManagemnt(option,pubkey,"",session_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        peer_pubkey = s;
                        Toast.makeText(panel.this, "" + s, Toast.LENGTH_SHORT).show();
                    }
                }));
    }


    private String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numchars);
    }





    static {
        System.loadLibrary("chilkat");
    }
}