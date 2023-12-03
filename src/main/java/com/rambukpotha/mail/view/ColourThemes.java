package com.rambukpotha.mail.view;

public enum ColourThemes {
    LIGHT,
    DEFAULT,
    DARK;

    public static String getCssPath(ColourThemes colourThemes) {
        return switch (colourThemes) {
            case LIGHT -> "/com/rambukpotha/mail/css/lightTheme.css";
            case DARK -> "/com/rambukpotha/mail/css/darkTheme.css";
            case DEFAULT -> "/com/rambukpotha/mail/css/defaultTheme.css";
        };
    }
}
