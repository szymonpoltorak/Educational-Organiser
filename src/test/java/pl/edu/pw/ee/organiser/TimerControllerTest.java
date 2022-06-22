package pl.edu.pw.ee.organiser;

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
    void checkIfInitiallyReset(){
        //given
        boolean expected=true;

        //When
        boolean answer=timerController.checkIfReset();

        //Then
        Assertions.assertEquals(expected,answer);
    }
}