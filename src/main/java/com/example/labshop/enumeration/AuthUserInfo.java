package com.example.labshop.enumeration;

public enum AuthUserInfo {
    USER_DATA_IS_EMPTY("nickname","Недостаточно информации о пользователе"),
    USER_EMAIL_IS_INVALID("email","Введенный адрес эл.почты не валидный"),
    USER_PASSWORD_IS_INVALID("password","Введенный пароль не валидный"),
    USER_IS_EXIST("email","Пользователь уже существует"),
    USER_EMAIL_IS_NOT_EXIST("email", "Пользователь с такой эл.почтой не сущуствует"),
    USER_PASSWORD_IS_INCORRECT("password", "Неверный пароль"),
    SUCCESS("none","Пользователь создан успешно");

    private final String type;
    private final String message;

    AuthUserInfo(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }
}
