package com.luvris2.memoapp.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.luvris2.memoapp.R;
import com.luvris2.memoapp.data.DatabaseHandler;
import com.luvris2.memoapp.model.Memo;

public class EditMemoActivity extends AppCompatActivity {

    Button btnEdit;
    EditText editTitle, editContent;
    Memo memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memo);

        btnEdit = findViewById(R.id.btnSave);
        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);

        // 넘겨받은 데이터 화면에 셋팅
        memo = (Memo) getIntent().getSerializableExtra("memo");
        editTitle.setText(memo.title);
        editContent.setText(memo.content);

        btnEdit.setOnClickListener(view -> {
            String title = editTitle.getText().toString();
            String content = editContent.getText().toString();

            if ( title.isEmpty() || content.isEmpty() ){
                Toast.makeText(getApplicationContext(), "데이터 입력을 올바르게 해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            // DB Test, update data
            DatabaseHandler db = new DatabaseHandler(EditMemoActivity.this);
            // update
            memo.title = title;
            memo.content = content;
            db.updateMemo(memo);
            db.close();

            Toast.makeText(getApplicationContext(), "메모가 수정되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}