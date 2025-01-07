package com.bookrental.helper;

import lombok.Getter;

@Getter
public enum OrderBy {
    NAME("name"),
    DESCRIPTION("description"),
    PUBLISHED_DATE("published_date");

    private String text;

    OrderBy(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
