package com.example.architectures_practise.mvp;

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

public class MVPActivity extends AppCompatActivity implements CountriesPresenter.View {

    private List<String> listValues = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private CountriesPresenter presenter;
    private Button retryBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        setTitle("MVP");

        presenter = new CountriesPresenter(this);

        listView = findViewById(R.id.list);
        retryBtn = findViewById(R.id.btn_retry);
        progressBar = findViewById(R.id.progress);
        adapter = new ArrayAdapter<>(this, R.layout.row_item_layout, R.id.tv_name, listValues);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MVPActivity.this, "you click" + listValues.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setListValues(List<String> countries) {
        listValues.clear();
        listValues.addAll(countries);
        retryBtn.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        Toast.makeText(this, getString(R.string.error_get_countries), Toast.LENGTH_SHORT).show();
        retryBtn.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    /**
     * retry btnクリック controllerに処理を依頼する
     */
    public void onRetry(View view) {
        presenter.doRefresh();

        retryBtn.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MVPActivity.class);
    }
}
