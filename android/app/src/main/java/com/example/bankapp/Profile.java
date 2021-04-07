package com.example.bankapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Profile extends AppCompatActivity {

    TextView textView;
    String code;
    LinearLayout layout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();

        layout = findViewById(R.id.profileGroup);
        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);
        Button clickButton = findViewById(R.id.logoutButton);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        if (appLinkData.getQueryParameterNames().contains("error")) {
            setDataToList("Access denied");
        } else {
            code = appLinkData.getQueryParameter("code");

            layout.setVisibility(View.GONE);

            getUserData();
        }
    }

    public void logout() {
        try {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUserData() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://your-awesome-site-api-url.uz/get-user-data?code=" + code;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();

                    Profile.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setDataToList(result);
                        }
                    });
                }
            }
        });
    }

    public void setDataToList(String json) {
        layout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        textView.setText(json);
    }
}