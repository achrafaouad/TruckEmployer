package com.fstt.recycleview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class profil extends AppCompatActivity {
    private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        txt = findViewById(R.id.textme);
        Intent intent = getIntent();
        String msg = intent.getStringExtra("name");
        txt.setText(msg);

    }
}