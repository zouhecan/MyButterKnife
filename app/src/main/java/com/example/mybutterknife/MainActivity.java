package com.example.mybutterknife;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    @BindView(R.id.contentTv)
    TextView contentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity$Binding mainActivity$Binding = new MainActivity$Binding(this);
        contentTv.setText(mainActivity$Binding.getAuthor());
    }
}