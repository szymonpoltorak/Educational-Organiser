package com.example.demo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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
    public void checkIfInitiallyReset(){
        //When
        boolean expected=false;
        //Then
        boolean answer= schoolTimetableController.setHoursAndDaysTable();
        Assertions.assertEquals(expected,answer);
    }
}