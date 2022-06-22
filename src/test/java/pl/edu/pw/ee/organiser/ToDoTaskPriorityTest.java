package pl.edu.pw.ee.organiser;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class ToDoTaskPriorityTest {
    private TaskPriority taskPriority;
    private FileWriter fileWriter;
    private final File priorities = new File("DB/TasksInfo/priorities");

    @BeforeEach
    void init_test_instances(){
        taskPriority = new TaskPriority();
    }

    @AfterEach
    void deleteChanges() throws IOException {

        StringBuilder buffer = new StringBuilder();
        Scanner wholeFile = new Scanner(priorities);
        String addedLine = "#taskName testName #priority 10";
        String current;

        while (wholeFile.hasNextLine()) {
            current = wholeFile.nextLine();
            if(!current.trim().isEmpty()) {
                buffer.append(current).append(System.lineSeparator());
            }
        }
        String fileContents = buffer.toString();
        wholeFile.close();

        fileContents = fileContents.replaceAll(addedLine, "");

        fileWriter = new FileWriter(priorities);
        fileWriter.write(fileContents);
        fileWriter.close();
    }

    @Test
    void getTaskPriority_test_pass() throws IOException {
        //given
        int testPriority = 10;
        String taskName = "testName";
        int result;
        int expected = testPriority;

        try {
            fileWriter = new FileWriter(priorities, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        fileWriter.write("#taskName " + taskName + " #priority " + testPriority);
        fileWriter.close();

        //when
        result = taskPriority.getTaskPriority(taskName);

        //then
        Assertions.assertEquals(result, expected);
    }
}
