package com.example.demo;

import org.jetbrains.annotations.NotNull;

public class CalcUtils {
    public static @NotNull String getLogString(double number){
        return String.valueOf(Math.log10(number));
    }

    public static @NotNull String getAbsString(double number){
        return String.valueOf(Math.abs(number));
    }

    public static @NotNull String getPower2String(double number){
        return String.valueOf(Math.pow(number, 2));
    }

    public static @NotNull String getSinString(double number) {
        return String.valueOf(Math.sin(number));
    }

    public static @NotNull String getCosString(double number) {
        return String.valueOf(Math.cos(number));
    }

    public static @NotNull String getTanString(double number) {
        return String.valueOf(Math.tan(number));
    }

    public static @NotNull String getCotString(double number) {
        return String.valueOf(Math.cos(number) / Math.sin(number));
    }

    public static @NotNull String getXpowerYString(double number1, double number2) {
        return String.valueOf(Math.pow(number1, number2));
    }

    public static @NotNull String getSqrtString(double number) {
        return String.valueOf(Math.sqrt(number));
    }

}
