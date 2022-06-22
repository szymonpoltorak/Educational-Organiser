package pl.edu.pw.ee.organiser;

import javafx.scene.control.DatePicker;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ToDoDeadline {
    private static final File priorities = new File("DB/TasksInfo/priorities");
    private static FileWriter fileWriter;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LocalDate getTaskDeadline(@NotNull String taskName) throws FileNotFoundException {
        LocalDate deadline;
        var taskNameWithoutSpaces = taskName.replace(" ", "_");

        try (var findDeadline = new Scanner(priorities)) {
            while (findDeadline.hasNext()) {
                var current = findDeadline.next();

                while (!current.equals("#taskName")) {
                    if (!findDeadline.hasNext()) {
                        return null;
                    }
                    current = findDeadline.next();
                }
                var taskNameFromFile = findDeadline.next();

                if (taskNameFromFile.equals(taskNameWithoutSpaces)) {
                    current = findDeadline.next();

                    while (!current.equals("#deadline")) {

                        if (!findDeadline.hasNext()) {
                            return null;
                        }
                        current = findDeadline.next();
                    }
                    current = findDeadline.next();

                    if (current.equals("null")) {
                        return null;
                    }
                    deadline = LocalDate.parse(current, formatter);
                    return deadline;
                }
            }
        }
        return null;
    }

    public void changeDeadline(String taskName, DatePicker deadlineDatePicker) throws IOException {
        if(taskName == null){
            return;
        }
        String wholeLine = null;
        String deadlineFromFile = null;
        String wholeFile = getWholeFile();
        var taskNameWithoutSpaces = taskName.replace(" ", "_");

        try (var findTask = new Scanner(priorities); var getWholeLine = new Scanner(priorities)) {
            while (findTask.hasNext() && getWholeLine.hasNextLine()) {
                wholeLine = getWholeLine.nextLine();
                var current = findTask.next();

                while (!current.equals("#taskName")) {
                    if (!findTask.hasNext()) {
                        return;
                    }
                    current = findTask.next();
                }
                var taskNameFromFile = findTask.next();

                if (taskNameFromFile.equals(taskNameWithoutSpaces)) {
                    while (!current.equals("#deadline")) {
                        if (!findTask.hasNext()) {
                            return;
                        }
                        current = findTask.next();
                    }
                    deadlineFromFile = findTask.next();
                    break;
                }
            }
        }
        fileWriter = new FileWriter(priorities, true);

        var deadline = String.valueOf(deadlineDatePicker.getValue());

        assert wholeLine != null;
        assert deadlineFromFile != null;
        var changedLine = wholeLine.replaceAll(deadlineFromFile, deadline);
        var changedFile = wholeFile.replaceAll(wholeLine, changedLine);

        fileWriter = new FileWriter(priorities);
        fileWriter.write(changedFile);
        fileWriter.close();
    }

    public static @NotNull String getWholeFile() throws FileNotFoundException {
        var buffer = new StringBuilder();

        try (var wholeFile = new Scanner(priorities)) {
            while (wholeFile.hasNextLine()) {
                buffer.append(wholeFile.nextLine()).append(System.lineSeparator());
            }
            return buffer.toString();
        }
    }

    public void setDeadlineNull(String taskName) throws IOException {
        if(taskName == null){
            return;
        }
        String wholeLine = null;
        var wholeFile = getWholeFile();
        var taskNameWithoutSpaces = taskName.replace(" ", "_");

        try (var findTask = new Scanner(priorities); var getWholeLine = new Scanner(priorities)) {
            while (findTask.hasNext() && getWholeLine.hasNextLine()) {
                wholeLine = getWholeLine.nextLine();
                var current = findTask.next();

                while (!current.equals("#taskName")) {

                    if (!findTask.hasNext()) {
                        return;
                    }
                    current = findTask.next();
                }
                var taskNameFromFile = findTask.next();

                if (taskNameFromFile.equals(taskNameWithoutSpaces)) {
                    break;
                }
            }
        }

        fileWriter = new FileWriter(priorities, true);
        assert wholeLine != null;
        var changedFile = wholeFile.replaceAll(wholeLine, wholeLine + " #deadline null");

        fileWriter = new FileWriter(priorities);
        fileWriter.write(changedFile);
        fileWriter.close();
    }

}
