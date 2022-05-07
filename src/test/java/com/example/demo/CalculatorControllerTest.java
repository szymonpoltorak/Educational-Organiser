package com.example.demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorControllerTest {

    private static CalculatorController calc=new CalculatorController();

    @BeforeAll
    static void initCalc(){
        calc=new CalculatorController();
    }

    @Test
    void CreatingValueNormalTesting() {

        //Given

        double num1=5;
        double num2=3;

        //When

        double testAdding=8;
        double testMultiplying=15;
        double testDividing=(double)5/3;
        double testPower=Math.pow(num1,num2);

        //Then

        assertEquals(testAdding,calc.create(num1,num2,"+"));
        assertEquals(testMultiplying,calc.create(num1,num2,"x"));
        assertEquals(testDividing,calc.create(num1,num2,"÷"));
        assertEquals(testPower,calc.create(num1,num2,"x^y"));
    }

    @Test
    void CreatingValueWithZeroAsNum2() {

        //Given

        double num1=1000;
        double num2=0;

        //When

        double testAdding=1000;
        double testMultiplying=0;
        double testDividing=-1;
        double testPower=Math.pow(num1,num2);

        //Then

        assertEquals(testAdding,calc.create(num1,num2,"+"));
        assertEquals(testMultiplying,calc.create(num1,num2,"x"));
        assertEquals(testDividing,calc.create(num1,num2,"÷"));
        assertEquals(testPower,calc.create(num1,num2,"x^y"));
    }

    @Test
    void CreatingValueWithoutOperatorGiven() {

        //Given

        double num1=1000;
        double num2=1000;

        //When

        double testAdding=0;
        double testMultiplying=0;
        double testDividing=0;
        double testPower=0;

        //Then

        assertEquals(testAdding,calc.create(num1,num2,""));
        assertEquals(testMultiplying,calc.create(num1,num2,""));
        assertEquals(testDividing,calc.create(num1,num2,""));
        assertEquals(testPower,calc.create(num1,num2,""));
    }

}