package com.luvris2.memoapp.model;

import java.io.Serializable;

public class Memo implements Serializable {
    // 주소록에 추가된 데이터를 다루는 클래스
    public int id;
    public String title;
    public String content;

    public Memo(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Memo(String title, String content) {
        this.title = title;
        this.content = content;
    }
}