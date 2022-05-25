package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

class ToDoControllerTest {
    @Test
    void getTaskNote_test_pass(){
        //given
        var expected = "test\\resources\\testdata\\test";
        var file = new File("src/test/resources/testdata/test.txt");

        //when
        var result = ToDoController.getProperString(file.toString());

        //then
        Assertions.assertEquals(expected, result);
    }
}
