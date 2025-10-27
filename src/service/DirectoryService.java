package service;

import exception.AlreadyExistingDirectory;
import model.Directory;

import java.util.List;

public interface DirectoryService {

    /**
     * Creates the directory if it doesn't already exist, including any necessary parent directories
     *
     * @param path the string representing the path where the directory should be created
     *
     * @return a Directory object representing the newly created directory
     *
     * @throws exception.AlreadyExistingDirectory if an I/O error occurs
     */
    Directory createDirectory(String path) throws AlreadyExistingDirectory;

    /**
     * Retrieves a Directory object by its path
     *
     * @param path the string representing the path of the directory to be retrieved
     *
     * @return the Directory object with the specified path, or null if no such directory exists
     */
    Directory getDirectoryByPath(String path);

    /**
     * Saves the specified directory in the memory
     *
     *  @param directory the Directory object to be saved. Must not be null
     */
    void saveDirectory(Directory directory) throws AlreadyExistingDirectory;

    /**
     * Deletes the specified directory and all its contents from the file system
     *
     * @param directory the Directory to be deleted. Must not be null
     */
    void deleteDirectory(Directory directory);

    /**
     * Retrieves a Directory object by its name
     *
     * @param directoryName the name of the directory to be retrieved
     *
     * @return the Directory object with the specified name, or null if no such directory exists
     */
    Directory getDirectory(String directoryName);

    /**
     * Retrieves a list of all directories currently stored in memory
     *
     * @return a List of Directory objects representing all directories; the list may be empty if no directories are present
     */
    List<Directory> getAllDirectories();
}