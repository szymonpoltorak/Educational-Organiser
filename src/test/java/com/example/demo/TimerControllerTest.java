package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class TimerControllerTest {

    private TimerController timerController;

    @BeforeEach
    public void initTimerTest(){
        timerController = new TimerController();
    }

    @Test
    public void checkIfInitiallyReset(){

        //When
        boolean expected=true;

        //Then

        boolean answer=timerController.checkIfReset();

        Assertions.assertEquals(expected,answer);
    }
}