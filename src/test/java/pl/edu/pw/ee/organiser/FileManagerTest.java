package pl.edu.pw.ee.organiser;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

class FileManagerTest {
    private FileManager fileManager;
    private NoteController noteController;

    @BeforeEach
    public void init_test_instances(){
        fileManager = new FileManager();
        noteController = new NoteController();
    }

    @Test
    void addNewFolder_test_pass(){
        //given
        String folderName = "NewTestFolder";
        File dir = new File(NoteController.notesFolder.getAbsolutePath() + "//" + folderName);
        String expected = "success";

        if(dir.exists()){
            dir.delete();
        }

        //when
        String result = fileManager.addNewFolderFM(folderName);

        //then
        Assertions.assertEquals(expected, result);
        dir.delete();
    }

    @Test
    void addNewNote_test_pass() throws IOException {
        //given
        String fileName = "NewTestFile";
        TreeItem<String> notImportantItem = new TreeItem<>();
        String path = NoteController.notesFolder.getAbsolutePath();
        File dir = new File(path + "//" + fileName);
        String expected = "success";

        if(dir.exists()){
            dir.delete();
        }

        //when
        String result = fileManager.addNewNoteFM(fileName, notImportantItem, path);

        //then
        Assertions.assertEquals(expected, result);
        dir.delete();
    }

    @Test
    void deleteFolderOrNoteFM_test_pass() throws IOException {
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
