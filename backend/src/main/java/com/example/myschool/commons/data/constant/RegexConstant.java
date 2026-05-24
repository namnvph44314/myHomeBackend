package com.example.myschool.commons.data.constant;

public class RegexConstant {
    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
    public static final String EMAIL_PATTERN = "([a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,32}";
    public static final String PHONE_NUMBER_PATTERN = "^(0|84|\\+84)(2[0-9]|3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$";
    public static final String MOBILE_PATTERN = "^(2[0-9]|3[2-9]|5[6|8|9|2]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$";
    public static final String LANDLINE_PATTERN = "^[2-9][0-9]{9}$";
    public static final String TELEPHONE_PATTERN = "(" + LANDLINE_PATTERN + ")|(" + MOBILE_PATTERN + ")";
    public static final String TYPE_API_PUSH = "^(classify|extract_only|ocr_extract|ocr_bulk_extract|ocr)$";
    public static final String TYPE_API_RECEIVE = "^(ocr_bulk_extract)$";
    public static final String STATUS_API_PUSH_RECEIVE = "^(active|deactive)$";
    public static final String VALIDATE_URL = "^\\s*(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.\\S{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+" +
            "[a-zA-Z0-9]\\.\\S{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]" +
            "+\\.[a-zA-Z0-9\\~\\`\\!\\@\\#\\$\\%\\^\\&\\*\\)\\(\\-\\_\\+\\=\\}\\{\\:\\;\\<\\,\\>\\.\\?\\/]{2,}" +
            "|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,})\\s*$";

    public static final String YOB = "^\\s*(\\d{2}/\\d{2}/\\d{4}|\\d{4}|\\d{2}/\\d{4})\\s*$";
    public static final String DOB = "^\\s*(\\d{2}/\\d{2}/\\d{4})\\s*$";
    public static final String IS_NUMBER_STRING = "(\\d+)";

    public static final String CCCD_NUMBER = "^\\d{9}|\\d{12}$";

    public static final String BUSINESS_CODE = "^\\d{10}|\\d{13}$";
}
