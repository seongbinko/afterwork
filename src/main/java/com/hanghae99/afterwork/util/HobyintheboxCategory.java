package com.hanghae99.afterwork.util;

import lombok.Getter;

@Getter
public enum HobyintheboxCategory {
    life("라이프소품키트",74),
    soap("비누향캔들",282),
    sil("실과-바늘",53),
    jasu("자수펀치니들",288),
    art("미술키트",54),
    paperart("페이퍼아트",287),
    mealkit("밀키트",274),
    baking("베이킹데코키트",57),
    leathercraft("가죽공예",60),
    puzzle("퍼즐조립브릭",135),
    woodratancraft("우드라탄공예",71),
    beadscraft("비즈공예",73),
    gardening("가드닝",59);

    private String krCategory;
    private int num;

    HobyintheboxCategory(String krCategory, int num) {
        this.krCategory = krCategory;
        this.num = num;

    }
}

