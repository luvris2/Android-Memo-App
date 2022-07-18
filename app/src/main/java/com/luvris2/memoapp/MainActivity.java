package com.luvris2.memoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.luvris2.memoapp.adapter.MemoAdapter;
import com.luvris2.memoapp.data.DatabaseHandler;
import com.luvris2.memoapp.model.Memo;
import com.luvris2.memoapp.ui.AddMemoActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    RecyclerView recyclerView;
    MemoAdapter adapter;
    ArrayList<Memo> memoList;
    ImageView imgSearch, imgMain;
    EditText editSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgSearch = findViewById(R.id.imgSearch);
        imgMain = findViewById(R.id.imgMain);
        editSearch = findViewById(R.id.editSearch);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // 연락처 추가 액티비티로 이동
        btnAdd = findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddMemoActivity.class);
            startActivity(intent);
        });

        // 메모 검색
        imgSearch.setOnClickListener(view -> {
            // EditText에서 검색어 저장
            String keyword = editSearch.getText().toString().trim();
            // 가져온 검색어로 DB의 데이터 검색
            DatabaseHandler db = new DatabaseHandler(MainActivity.this);
            memoList = db.searchMemo(keyword);

            // 검색 결과 출력
            adapter = new MemoAdapter(MainActivity.this, memoList);
            recyclerView.setAdapter(adapter);
        });

        // 메모 메인
        imgMain.setOnClickListener(view -> {
            DatabaseHandler db = new DatabaseHandler(MainActivity.this);
            memoList = db.getAllMemo();

            adapter = new MemoAdapter(MainActivity.this, memoList);

            recyclerView.setAdapter(adapter);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // DB에 메모를 가져와 리사이클러뷰에 출력
        DatabaseHandler db = new DatabaseHandler(MainActivity.this);
        memoList = db.getAllMemo();

        adapter = new MemoAdapter(MainActivity.this, memoList);

        recyclerView.setAdapter(adapter);
    }
}