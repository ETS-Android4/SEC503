package com.example.myapplication;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Retrofit.INodeJS;
import com.example.myapplication.Retrofit.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class panel extends AppCompatActivity {



    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    KeyGenerator keygen = KeyGenerator.getInstance("AES");
    Cipher cipher = Cipher.getInstance("RSA");


    Random rand = new Random();

    MaterialButton request,accept,reject,askpub;
    TextView data,Info;

    String session_id,private_key,public_key,user_id;
    String peer_pubkey;

    String option,responded_status;


    public panel() throws NoSuchAlgorithmException, NoSuchPaddingException {
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
        }


        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);


        request.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                option = "req";
                responded_status = "binding";
                String request_id = String.format("%04d", rand.nextInt(10000));
//                String email2 = "temp@temp.com";
                ChatManagement(request_id,option,user_id,data.getText().toString(),responded_status,null);

            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option = "res";
                responded_status = "accept";


                keygen.init(256);
                SecretKey GenKey = keygen.generateKey();
                String key = GenKey.getEncoded().toString();

                byte[] ff = Base64.decode(peer_pubkey,Base64.NO_WRAP);
                PublicKey publicKey= null;
                try {
                    publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(ff));
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }


                try {
                    cipher.init(Cipher.ENCRYPT_MODE,publicKey);
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }


                byte [] keybyte = key.getBytes(StandardCharsets.UTF_8);

                byte [] cipherdata = null;
                try {
                    cipherdata = cipher.doFinal(keybyte);
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }




                String dd = Base64.encodeToString(cipherdata,Base64.DEFAULT);

//                Toast.makeText(panel.this,""+dd,Toast.LENGTH_SHORT).show();


                String chatting_id = String.format("%04d", rand.nextInt(10000));
                ChatManagement(data.getText().toString(),option,"","",responded_status,chatting_id);
                ShareKey(chatting_id,dd);
                Toast.makeText(panel.this,""+dd,Toast.LENGTH_SHORT).show();

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
        compositeDisposable.add(myAPI.ChatManagemnt("chk_res",null,"","asd556717@gmail.com","binding",null)
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





}