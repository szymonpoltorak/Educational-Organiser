package pl.edu.pw.ee.organiser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SchoolTimetableControllerTest {
    private SchoolTimetableController schoolTimetableController;

    @BeforeEach
    void setUp() {
        schoolTimetableController = new SchoolTimetableController();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void checkIfInitiallyReset(){
        //given
        boolean expected=false;

        //When
        boolean answer= schoolTimetableController.setHoursAndDaysTable();

        //Then
        Assertions.assertEquals(expected,answer);
    }
}