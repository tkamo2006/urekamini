package com.uplus.miniproject2.entity.proflie;

public enum RequestStatus {
    APPROVED("승인"), REJECTED("거절");

    private String name;

    RequestStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
