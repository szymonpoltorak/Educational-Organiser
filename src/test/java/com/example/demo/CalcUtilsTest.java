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

    @Test
    public void getSinString_test_pass(){
        //given
        double radian = 1;
        String expected = "0.8414709848078965";

        //when
        String result = CalcUtils.getSinString(radian);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void getCosString_test_pass(){
        //given
        double radian = 1;
        String expected = "0.5403023058681398";

        //when
        String result = CalcUtils.getCosString(radian);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void getTanString_test_pass(){
        //given
        double radian = 1;
        String expected = "1.5574077246549023";

        //when
        String result = CalcUtils.getTanString(radian);

        //then
        Assertions.assertEquals(expected, result);
    }
}