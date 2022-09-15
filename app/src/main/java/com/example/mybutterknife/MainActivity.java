package com.example.mybutterknife;

import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @MyAnnotation(
            name = "<hc.zou>",
            text = "Demo for ButterKnife "
    )
    private View btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
    }
}