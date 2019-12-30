package com.example.architectures_practise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.architectures_practise.mvc.MVCActivity;
import com.example.architectures_practise.mvp.MVPActivity;
import com.example.architectures_practise.mvvm.MVVMActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("MAIN");
    }

    public void onMVC(View view) {
        startActivity(MVCActivity.getIntent(this));
    }

    public void onMVP(View view) {
        startActivity(MVPActivity.getIntent(this));

    }

    public void onMVVM(View view) {
        startActivity(MVVMActivity.getIntent(this));
    }
}
