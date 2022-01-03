package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chilkatsoft.CkCrypt2;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatActivity extends AppCompatActivity implements TextWatcher {


    SecretKey AES_key;
    String secret_key;
    private String name;
    private WebSocket webSocket;
    private EditText messageEdit;
    private View sendBtn, pickImgBtn;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;

    byte [] HMAC_KEY_BYTES;
    Mac mac;
    Key key;

    CkCrypt2 crypt = new CkCrypt2();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        name = getIntent().getStringExtra("name");
        secret_key = getIntent().getStringExtra("secret_key");
//        byte [] Encoded_key = Base64.decode(secret_key,Base64.DEFAULT);
//        AES_key = new SecretKeySpec(Encoded_key,0,Encoded_key.length, "AES");

        HMAC_KEY_BYTES = secret_key.getBytes(StandardCharsets.UTF_8);
        key = new SecretKeySpec(HMAC_KEY_BYTES,0,HMAC_KEY_BYTES.length,"HmacSHA1");


        crypt.put_CryptAlgorithm("aes");
        crypt.put_CipherMode("cbc");
        crypt.put_KeyLength(256);
        crypt.put_PaddingScheme(0);
        crypt.put_EncodingMode("hex");
        crypt.put_EncodingMode("str");
        String ivHex = "000102030405060708090A0B0C0D0E0F";
        crypt.SetEncodedIV(ivHex,"hex");
        crypt.SetEncodedKey(secret_key,"hex");


        initiateSocketConnection();

    }

    private void initiateSocketConnection() {

        OkHttpClient client = new OkHttpClient();
        String SERVER_PATH = "ws://192.168.100.85:6000";
        Request request = new Request.Builder().url(SERVER_PATH).build();
        webSocket = client.newWebSocket(request, new SocketListener());

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String string = s.toString().trim();

        if (string.isEmpty()) {
            resetMessageEdit();
        } else {

            sendBtn.setVisibility(View.VISIBLE);
            pickImgBtn.setVisibility(View.INVISIBLE);
        }

    }

    private void resetMessageEdit() {

        messageEdit.removeTextChangedListener(this);

        messageEdit.setText("");
        sendBtn.setVisibility(View.INVISIBLE);
        pickImgBtn.setVisibility(View.VISIBLE);

        messageEdit.addTextChangedListener(this);

    }

    private class SocketListener extends WebSocketListener {

        @Override
        public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
            super.onOpen(webSocket, response);

            runOnUiThread(() -> {
                Toast.makeText(ChatActivity.this,
                        "Socket Connection Successful!",
                        Toast.LENGTH_SHORT).show();

                initializeView();
            });

        }

        @Override
        public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {

            try {
                mac = Mac.getInstance("HmacSHA1");
                mac.init(key);
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                e.printStackTrace();
            }


            super.onMessage(webSocket, text);
            String decStr = crypt.decryptStringENC(text);

            runOnUiThread(() -> {

                try {
                    JSONObject jsonObject = new JSONObject(decStr);
                    jsonObject.put("isSent", false);

                    messageAdapter.addItem(jsonObject);

                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            });

        }
    }



    private void initializeView() {

        messageEdit = findViewById(R.id.messageEdit);
        sendBtn = findViewById(R.id.sendBtn);
        pickImgBtn = findViewById(R.id.pickImgBtn);

        recyclerView = findViewById(R.id.recyclerView);

        messageAdapter = new MessageAdapter(getLayoutInflater());
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            mac = Mac.getInstance("HmacSHA1");
            mac.init(key);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }


        messageEdit.addTextChangedListener(this);

        sendBtn.setOnClickListener(v -> {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", name);
                jsonObject.put("message", messageEdit.getText().toString());



                String encStr = crypt.encryptStringENC(jsonObject.toString());
                webSocket.send(encStr);

                jsonObject.put("isSent", true);
                messageAdapter.addItem(jsonObject);

                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

                resetMessageEdit();

            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }


    static {
        System.loadLibrary("chilkat");
    }

}