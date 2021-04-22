package com.example.bankapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button clickButton = findViewById(R.id.loginButton);
        Switch authSwitch = findViewById(R.id.authSwitch);

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientId = YOUR_CLIENT_ID;
                String redirectUri = "uzinfocom://bank";

                String url = String.format("https://myid.uz/api/v1/oauth2/authorization?client_id=%s&redirect_uri=%s&response_type=%s&method=%s&scope=%s",
                        clientId,
                        redirectUri,
                        "code",
                        authSwitch.isChecked() ? "strong" : "simple",
                        "common_data,doc_data,contacts,address"
                );
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }
}