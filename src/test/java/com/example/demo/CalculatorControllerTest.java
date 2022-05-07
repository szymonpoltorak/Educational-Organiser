package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorControllerTest {

    private final CalculatorController calc=new CalculatorController();

    @Test
    void CreatingValueNormalTesting() {

        //Given numbers

        double num1=5;
        double num2=3;

        //Expected values

        double testAdding=8;
        double testMultiplying=15;
        double testDividing=(double)5/3;
        double testPower=Math.pow(num1,num2);

        //Actual tests

        assertEquals(testAdding,calc.create(num1,num2,"+"));
        assertEquals(testMultiplying,calc.create(num1,num2,"x"));
        assertEquals(testDividing,calc.create(num1,num2,"÷"));
        assertEquals(testPower,calc.create(num1,num2,"x^y"));
    }

    @Test
    void CreatingValueWithZeroAsNum2() {

        //Given numbers

        double num1=1000;
        double num2=0;

        //Expected values

        double testAdding=1000;
        double testMultiplying=0;
        double testDividing=-1;
        double testPower=Math.pow(num1,num2);

        //Actual tests

        assertEquals(testAdding,calc.create(num1,num2,"+"));
        assertEquals(testMultiplying,calc.create(num1,num2,"x"));
        assertEquals(testDividing,calc.create(num1,num2,"÷"));
        assertEquals(testPower,calc.create(num1,num2,"x^y"));
    }

    @Test
    void CreatingValueWithoutOperatorGiven() {

        //Given numbers

        double num1=1000;
        double num2=1000;

        //Expected values

        double testAdding=0;
        double testMultiplying=0;
        double testDividing=0;
        double testPower=0;

        //Actual tests

        assertEquals(testAdding,calc.create(num1,num2,""));
        assertEquals(testMultiplying,calc.create(num1,num2,""));
        assertEquals(testDividing,calc.create(num1,num2,""));
        assertEquals(testPower,calc.create(num1,num2,""));
    }
}