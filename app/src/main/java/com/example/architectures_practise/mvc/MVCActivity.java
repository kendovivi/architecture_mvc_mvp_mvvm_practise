package com.example.architectures_practise.mvc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.architectures_practise.R;

import java.util.ArrayList;
import java.util.List;

public class MVCActivity extends AppCompatActivity {

    private List<String> listValues = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private CountriesController controller;
    private Button retryBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvc);
        setTitle("MVC");

        controller = new CountriesController(this);

        listView = findViewById(R.id.list);
        retryBtn = findViewById(R.id.btn_retry);
        progressBar = findViewById(R.id.progress);
        adapter = new ArrayAdapter<>(this, R.layout.row_item_layout, R.id.tv_name, listValues);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MVCActivity.this, "you click" + listValues.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * list viewのデータ設定 controllerでデータ取得完了した時点で呼ばれる
     *
     * @param values
     */
    public void setListValues(List<String> values) {
        listValues.clear();
        listValues.addAll(values);
        retryBtn.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();

    }

    /**
     * retry btnクリック controllerに処理を依頼する
     */
    public void onRetry(View view) {
        controller.doRefresh();

        retryBtn.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * 失敗時処理
     */
    public void onError() {
        Toast.makeText(this, getString(R.string.error_get_countries), Toast.LENGTH_SHORT).show();
        retryBtn.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MVCActivity.class);
    }
}