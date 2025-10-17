package service;

import exceptions.FilesLoadingException;
import exceptions.FilesSavingException;
import model.MediaFile;

import java.util.List;

public interface TextFileService {

    /**
     * Loads all the file paths from the text file and returns them as a list of strings
     *
     * @return a list of strings representing the file paths read from the text file
     *
     * @throws FilesLoadingException if an I/O error occurs during reading the file
     */
    List<String> loadFilesFromTextFile() throws FilesLoadingException;

    /**
     * Saves the provided list of media files to the text file
     *
     * @param mediaFiles the list of MediaFile objects to be saved to the text file
     *
     * @throws FilesSavingException if any I/O error occurs during the file writing process
     */
    void saveFilesToTextFile(List<MediaFile> mediaFiles) throws FilesSavingException;
}