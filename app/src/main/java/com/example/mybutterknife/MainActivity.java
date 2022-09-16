package com.example.mybutterknife;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    @BindView(R.id.contentTv)
    TextView contentTv;

    @OnClick(R.id.contentTv)
    public void changeContent(View v) {
        contentTv.setText("你点了下");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity$Binding mainActivity$Binding = new MainActivity$Binding(this);
    }
}