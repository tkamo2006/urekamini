package com.uplus.miniproject2.entity.proflie;

public enum RequestType {
    REGISTER("등록"), UPDATE("수정");

    private String name;

    RequestType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
