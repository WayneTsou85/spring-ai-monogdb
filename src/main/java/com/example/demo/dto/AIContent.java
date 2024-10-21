package com.example.demo.dto;

import org.apache.commons.lang3.StringUtils;

public record AIContent(String message, boolean isEnd) {
    public AIContent(String message, boolean isEnd) {
        this.message = message;
        this.isEnd = isEnd;
    }

    public AIContent(String message) {
        this(message, StringUtils.isBlank(message));
    }

    public AIContent() {
        this(null, true);
    }
}
