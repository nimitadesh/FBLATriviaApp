package com.nimitadeshpande.fblatrivia.utils;

public class FblaUtils {

    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }
}
