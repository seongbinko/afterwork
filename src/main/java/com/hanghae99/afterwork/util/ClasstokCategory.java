package com.hanghae99.afterwork.util;

import lombok.Getter;

@Getter
public enum ClasstokCategory {
    health("운동/건강"),
    life("라이프스타일"),
    food("음료/요리"),
    art("미술"),
    carrer("커리어"),
    craft("공예"),
    picture("사진/영상"),
    music("음악"),
    flanguage("외국어"),
    education("교육");

    private String krCategory;

    ClasstokCategory(String krCategory){
        this.krCategory = krCategory;
    }
}
