package com.hst.simplephotoedior.model;

/**
 * Created by Nick Bapu on 22-08-2018.
 */

public class FontDetail {
    String fontName;
    String fontPath;

    public FontDetail(String fontName, String fontPath) {
        this.fontName = fontName;
        this.fontPath = fontPath;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontPath() {
        return fontPath;
    }

    public void setFontPath(String fontPath) {
        this.fontPath = fontPath;
    }
}
