package pl.edu.pw.ee.organiser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AverageControllerTest {


    private static AverageController averageController;

    @BeforeEach
    public void initAverageController() {
        averageController = new AverageController();
    }

    @Test
    public void averageResultCheck(){

        //given
        double expected = 2.86;
        double restult;

        //when
        averageController.setSumGrade(20);
        averageController.setSumWeight(7);
        averageController.countResult();
        restult = averageController.getResults();

        //then
        Assertions.assertEquals(expected,restult);
    }

    @Test
    public void roundToTwoDecimalTest(){

        //given
        double expected = 3.41; //58/17
        double result;

        //when
        result = averageController.roundTo2DecimalPlace(3.41176);

        //then
        Assertions.assertEquals(expected,result);

    }

}
