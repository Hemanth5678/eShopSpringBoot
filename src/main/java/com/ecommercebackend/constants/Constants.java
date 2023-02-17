package com.ecommercebackend.constants;

public class Constants {

    public static final String EMAIL_REGEX = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}";
    public static final String PHONE_REGEX = "(\\+?\\d[\\d -]{8,12}\\d)\\b";

    public static final int MIN_LENGTH_PASSWORD = 6;
    public static final int MAX_LENGTH_PASSWORD = 24;
}
