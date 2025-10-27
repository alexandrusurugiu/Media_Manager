package service;

import exception.FilesLoadingException;
import exception.FilesSavingException;
import java.io.IOException;
import java.util.List;

public interface TextFileService {

    /**
     * Loads all the file paths from the text file and returns them as a list of strings
     *
     * @return a list of strings representing the file paths read from the text file
     *
     * @throws FilesLoadingException if an I/O error occurs during reading the file
     */
    List<String> loadFilesFromTextFile() throws IOException;

    /**
     * Saves the provided list of files to the text file
     *
     * @param pathsToBeSaved the list of files to be saved to the text file
     *
     * @throws FilesSavingException if any I/O error occurs during the file writing process
     */
    void saveFilesToTextFile(List<String> pathsToBeSaved) throws FilesSavingException;
}