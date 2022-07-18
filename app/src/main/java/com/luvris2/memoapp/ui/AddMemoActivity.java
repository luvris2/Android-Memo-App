package com.luvris2.memoapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.luvris2.memoapp.R;
import com.luvris2.memoapp.data.DatabaseHandler;
import com.luvris2.memoapp.model.Memo;

import java.util.ArrayList;

public class AddMemoActivity extends AppCompatActivity {

    Button btnSave;
    EditText editName, editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);

        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName = findViewById(R.id.editTitle);
                editPhone = findViewById(R.id.editContent);

                String name = editName.getText().toString();
                String phone = editPhone.getText().toString();

                if ( name.isEmpty() || phone.isEmpty() ){
                    Toast.makeText(getApplicationContext(), "데이터 입력을 올바르게 해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // DB Test, insert data
                DatabaseHandler db = new DatabaseHandler(AddMemoActivity.this);
                // insert
                Memo memo = new Memo(name, phone);
                db.addMemo(memo);

                // data access
                ArrayList<Memo> memoList = db.getAllMemo();
                for (Memo data : memoList) {
                    Log.i("MyMemo", "id : " + data.id + ", name : " + data.title + ", phone : " + data.content);
                }

                Toast.makeText(getApplicationContext(), "연락처가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}