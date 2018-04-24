package com.bheaver.ngl4.enums;

public enum UserType {

    ADMIN("A"),
    GENERAL("B");

    private String patronTpye;
    private UserType(String patronType) {
        this.patronTpye = patronType;
    }

    public String getPatronTpye() {
        return patronTpye;
    }
}
