package com.example.demo;

public class CalcUtils {
    public static String getLogString(double number){
        return String.valueOf(Math.log10(number));
    }

    public static String getAbsString(double number){
        return String.valueOf(Math.abs(number));
    }

    public static String getPower2String(double number){
        return String.valueOf(Math.pow(number, 2));
    }
}
