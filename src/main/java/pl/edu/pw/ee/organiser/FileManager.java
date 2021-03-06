package pl.edu.pw.ee.organiser;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileManager {
    private NoteController noteController = new NoteController();

    public String addNewFolderFM(String folderName){
        var fName = folderName;
        var folderPath = NoteController.notesFolder.getAbsolutePath();

        if(Objects.equals(fName, "")) {
            return "no name given";
        }

        var newFolder = new File(folderPath + "//" +fName);
        if(newFolder.exists()) {
            return "already exists";
        }

        if(!newFolder.mkdir()) {
            return "failed to create directory";
        }

        return "success";
    }
    public String addNewNoteFM(@NotNull String newNoteName, TreeItem<String> item, String currentPath) throws IOException {
        if(newNoteName.equals("")){
            return "no name given";
        }
        var newNote = new File(currentPath + "//" + newNoteName);

        if(newNote.exists()){
            return "already exists";
        }

        if (!newNote.createNewFile()){
            throw new IllegalStateException("I can't create new file!");
        }
        return "success";
    }

    public String deleteFolderOrNoteFM(TreeView<String> notesList, @NotNull Path currentPath) throws IOException{
        var file = currentPath.toFile();

        if (file.isDirectory() && Objects.requireNonNull(file.listFiles()).length > 0) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                if (!f.delete()){
                    System.out.println(f);
                    throw new IllegalStateException("I can't delete file!");
                }
            }
        }
        Files.delete(currentPath);

        return "success";
    }

}
