package com.example.api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvMain;
    ArrayAdapter listAdapter;
    CheckBox cbAsync;
    TextView tvInfo;
    List<String> listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMain = findViewById(R.id.lvMain);
        cbAsync = findViewById(R.id.cbAsync);
        tvInfo = findViewById(R.id.tvInfo);
    }

    public void onBtnStartClick(View view) {
        tvInfo.setText(getResources().getString(R.string.btnClicked));
        if (cbAsync.isChecked()) {
            getDataByAsyncTask();
        }
        else {
            getDataByThread();
        }
    }

    public void getDataByAsyncTask() {
        new AsyncDataLoader() {
            @Override
            public void onPostExecute(String result) {
                listView = new ArrayList<>();
                listView.add(result);
                listAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listView);
                lvMain.setAdapter(listAdapter);
                tvInfo.setText(getResources().getString(R.string.btnClickedResult));
            }
        }.execute(Constants.BMW_MAKE_MODEL_API_LINK);
    }

    public void getDataByThread() {
        Runnable getDataAndDisplayRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    final String result = ApiDataReader.getValuesFromApi();
                    Runnable updateUIRunnable = new Runnable() {
                        @Override
                        public void run() {
                            listView = new ArrayList<>();
                            listView.add(result);
                            listAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listView);
                            lvMain.setAdapter(listAdapter);
                            tvInfo.setText(getResources().getString(R.string.btnClickedResult));
                        }
                    };
                    runOnUiThread(updateUIRunnable);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(getDataAndDisplayRunnable);
        thread.start();
    }
}