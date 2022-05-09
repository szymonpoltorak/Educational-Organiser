package com.example.demo;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class FileManagerTest {

    FileManager fileManager = new FileManager();
    NoteController noteController = new NoteController();

    @Test
    public void addNewFolder_test_pass(){
        //given
        String folderName = "NewTestFolder";
        File dir = new File(NoteController.notesFolder.getAbsolutePath() + "//" + folderName);
        if(dir.exists()){dir.delete();}
        String expected = "success";

        //when
        String result = fileManager.addNewFolderFM(folderName);

        //then
        Assertions.assertEquals(expected, result);
        dir.delete();
    }

    @Test
    public void addNewNote_test_pass() throws IOException {
        //given
        String fileName = "NewTestFile";
        TreeItem<String> notImportantItem = new TreeItem<>();
        String path = NoteController.notesFolder.getAbsolutePath();
        File dir = new File(path + "//" + fileName);
        if(dir.exists()){dir.delete();}
        String expected = "success";


        //when
        String result = fileManager.addNewNoteFM(fileName, notImportantItem, path);

        //then
        Assertions.assertEquals(expected, result);
        dir.delete();
    }

    @Test
    public void deleteFolderOrNoteFM_test_pass() {
        //given
        String folderName = "FolderToDelete";
        TreeView<String> notImportantView = noteController.notesList;
        String path = NoteController.notesFolder.getAbsolutePath() + "//" + folderName;
        String expected = "success";


        //when
        String resultAddNewFolder = fileManager.addNewFolderFM(folderName);
        String resultDeleteFolder = fileManager.deleteFolderOrNoteFM(notImportantView, Path.of(path));

        //then
        Assertions.assertEquals(expected, resultDeleteFolder);
        Assertions.assertEquals(expected, resultAddNewFolder);

    }



}
