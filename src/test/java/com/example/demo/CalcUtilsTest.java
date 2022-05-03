package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalcUtilsTest {
    @Test
    public void getLogString_test_pass(){
        //given
        double number = 1;
        String expected = "0.0";

        //when
        String result = CalcUtils.getLogString(number);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void getPower2String_test_pass(){
        //given
        double number = 2;
        String expected = "4.0";

        //when
        String result = CalcUtils.getPower2String(number);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void getAbsString_test_pass(){
        //given
        double number = -4;
        String expected = "4.0";

        //when
        String result = CalcUtils.getAbsString(number);

        //then
        Assertions.assertEquals(expected, result);
    }
}