package com.tencent.qqlive.listviewdeleteanimationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<String> mStringList;
    private MyListAdapter mMyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.lv_content);
        mStringList = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            mStringList.add("" + i);
        }
        mMyListAdapter = new MyListAdapter(mStringList, this);
        mListView.setAdapter(mMyListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMyListAdapter.collapseDeleteView(mListView, position);
            }
        });
    }
}
