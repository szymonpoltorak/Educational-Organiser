package com.example.demo;

public class CalcUtils {
    public static String getLogString(double number){
        return String.valueOf(Math.log10(number));
    }

    public static String getAbsString(double number){
        return String.valueOf(Math.abs(number));
    }

    public static String getPower2String(double number){ return String.valueOf(Math.pow(number, 2)); }

    public static String getSinString(double number) { return String.valueOf(Math.sin(number)); }

    public static String getCosString(double number) { return String.valueOf(Math.cos(number)); }

    public static String getTanString(double number) { return String.valueOf(Math.sin(number)); }

    public static String getCotString(double number) { return String.valueOf(Math.cos(number) / Math.sin(number)); }
}
