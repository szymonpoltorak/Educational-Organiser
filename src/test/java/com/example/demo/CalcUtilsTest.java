package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalcUtilsTest {
    @Test
    void getLogString_test_pass(){
        //given
        double number = 1;
        String expected = "0.0";

        //when
        String result = CalcUtils.getLogString(number);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getPower2String_test_pass(){
        //given
        double number = 2;
        String expected = "4.0";

        //when
        String result = CalcUtils.getPower2String(number);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getAbsString_test_pass(){
        //given
        double number = -4;
        String expected = "4.0";

        //when
        String result = CalcUtils.getAbsString(number);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getSinString_test_pass(){
        //given
        double radian = 1;
        String expected = "0.8414709848078965";

        //when
        String result = CalcUtils.getSinString(radian);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getCosString_test_pass(){
        //given
        double radian = 1;
        String expected = "0.5403023058681398";

        //when
        String result = CalcUtils.getCosString(radian);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getTanString_test_pass(){
        //given
        double radian = 1;
        String expected = "1.5574077246549023";

        //when
        String result = CalcUtils.getTanString(radian);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getCotString_test_pass(){
        //given
        double radian = 1;
        String expected = "0.6420926159343308";

        //when
        String result = CalcUtils.getCotString(radian);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getXpowerYString_test_pass(){
        //given
        double num1 = 8;
        double num2 = 3;
        String expected = "512.0";

        //when
        String result = CalcUtils.getXpowerYString(8,3);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getSqrtString_test_pass(){
        //given
        double num1 = 1024;
        String expected = "32.0";

        //when
        String result = CalcUtils.getSqrtString(num1);

        //then
        Assertions.assertEquals(expected, result);
    }

}