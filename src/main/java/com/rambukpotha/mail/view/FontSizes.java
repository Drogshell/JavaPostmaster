package com.rambukpotha.mail.view;

public enum FontSizes {
    SMALL,
    MEDIUM,
    LARGE;

    public static String getCssPath(FontSizes fontSize){
        return switch (fontSize){
            case SMALL -> "/com/rambukpotha/mail/css/smallFont.css";
            case MEDIUM -> "/com/rambukpotha/mail/css/mediumFont.css";
            case LARGE -> "/com/rambukpotha/mail/css/largeFont.css";
        };
    }


}
