package com.sk.enu;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JpaEnum {

    ONE(1, "第一个"),
    TOW(2, "第二个");

    private int sort;
    private String name;
}
