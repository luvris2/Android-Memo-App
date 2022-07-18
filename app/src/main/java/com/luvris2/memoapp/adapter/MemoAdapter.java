package com.luvris2.memoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.luvris2.memoapp.R;
import com.luvris2.memoapp.data.DatabaseHandler;
import com.luvris2.memoapp.model.Memo;
import com.luvris2.memoapp.ui.EditMemoActivity;

import java.util.List;

// row 화면에 맵핑 할 어댑터 클래스 생성 순서, 주석의 번호순으로 확인하기
// 1. 어댑터 클래스 생성, RecyclerView.Adapter 상속, 추상 메소드 정의

// 5. 2번에서 생성한 ViewHolder 클래스를 RecyclerViewAdapter<>의 타입으로 설정 -> <MemoAdapter.ViewHolder>
public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {

    // 4. 어댑터 클래스의 멤버변수와 생성자 정의
    Context context;
    List<Memo> memoList;
    public MemoAdapter(Context context, List<Memo> memoList) {
        this.context = context;
        this.memoList = memoList;
    }

    // 6. RecyclerView.ViewHolder 메소드 구현, 어댑터 클래스 이름으로 변경 -> MemoAdapter.ViewHolder
    @NonNull
    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.memo_row, parent, false);
        return new MemoAdapter.ViewHolder(view);
    }

    // 메모리에 있는 데이터를 화면에 표시하는 메소드
    // 7. RecyclerView.ViewHolder 어댑터 클래스 이름으로 변경 -> MemoAdapter.ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MemoAdapter.ViewHolder holder, int position) {
        Memo memo = memoList.get(position);
        holder.txtTitle.setText(memo.title);
        holder.txtContent.setText(memo.content);
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    // 2. ViewHolder 클래스 생성, 생성자 정의
    // row.xml 화면에 있는 뷰를 연결 시키는 클래스 생성하기
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtContent;
        ImageView imgDelete;
        CardView cardView;

        // 3. 생성자에 뷰와 연결시키는 코드 작성
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtContent = itemView.findViewById(R.id.txtContent);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            cardView = itemView.findViewById(R.id.cardView);

            // 카드뷰 클릭 이벤트, 연락처 수정하기
            cardView.setOnClickListener(view -> {
                // 유저가 몇번째 행을 클릭했는지 인덱스 저장
                int index = getAdapterPosition();

                // 인덱스의 저장된 데이터 호출
                Memo memo = memoList.get(index);

                // 수정하는 액티비티로 데이터 전달
                Intent intent = new Intent(context, EditMemoActivity.class);
                intent.putExtra("memo", memo);
                context.startActivity(intent);
            });

            // 삭제모양 이미지 클릭 이벤트, 연락처 삭제하기
            imgDelete.setOnClickListener(view -> {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("메모 삭제");
                alert.setMessage("메모를 삭제하시겠습니까?");

                // 대화상자 '확인 버튼' 이벤트
                alert.setPositiveButton("삭제", (dialogInterface, i) -> {
                    // 유저가 몇번째 행을 클릭했는지 인덱스 저장
                    int index = getAdapterPosition();

                    // 인덱스의 저장된 데이터 호출
                    Memo memo = memoList.get(index);

                    DatabaseHandler db = new DatabaseHandler(context);
                    // delete, name&phone dummy
                    db.deleteMemo(memo);

                    // RecyclerView List Refresh
                    memoList.remove(index);
                    notifyDataSetChanged();
                });

                // 대화상자 '취소 버튼' 이벤트
                alert.setNegativeButton("취소", null);
                // 대화상자 버튼을 누르지 않으면 화면이 넘어가지 않게 설정
                alert.setCancelable(false);
                // 대화상자 화면에 표시
                alert.show();
            });
        }
    }
}
