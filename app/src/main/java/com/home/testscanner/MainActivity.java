package com.home.testscanner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.zxing.client.android.CaptureActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                String[] perms = {Manifest.permission.CAMERA};
                requestPermissions(perms, 112);
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivity(intent);
            }
        });
    }
}