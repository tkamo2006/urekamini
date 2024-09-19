package com.uplus.miniproject2.entity.hobby;

public enum HobbyCategory {

    CULTURAL_LIFE("문화생활"), EXERCISE("운동"), FOOD("음식"),
    GAME("게임"), MUSIC("음악"), TRAVEL("여행"), ETC("기타");

    private String name;

    HobbyCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

