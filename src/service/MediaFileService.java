package service;

import model.MediaFile;

import java.util.List;

public interface MediaFileService {

    /**
     * Creates a MediaFile object from the given path string, parsing the path to extract the file name, extension, file type, the path itself and the last modified date
     *
     * @param path the full path of the media file, which must include at least one component
     *
     * @return a MediaFile object with the name, path, file type, and last modified date set if the path
     */
    MediaFile createMediaFile(String path);

    /**
     * Saves the specified media file in the memory
     *
     * @param mediaFile the media file to be saved; must not be null
     */
    void saveMediaFile(MediaFile mediaFile);

    /**
     * Deletes the {@link model.MediaFile} object from the memory
     *
     * @param mediaFile the {@link model.MediaFile} object we want to remove from memory
     */
    void deleteMediaFile(MediaFile mediaFile);

    /**
     * Searches a {@link model.MediaFile} object based on the path of the file provided as the parameter
     *
     * @param path {@link String} variable which counts as the path of the wanted object
     *
     * @return the {@link model.MediaFile} objects that has the path equal with the one given as parameter
     */
    MediaFile getMediaFileByPath(String path);

    /**
     * Provides a list with all the existing media files in the memory, at that specific moment
     *
     * @return a {@link List} of {@link model.MediaFile} objects
     */
    List<MediaFile> getAllMediaFiles();

    /**
     * Checks if the given parameter is a media file by examining its extension
     *
     * @param filename the name of the file to check
     *
     * @return {@code true} if the filename has a media extension, {@code false} otherwise
     */
    boolean isMediaFile(String filename);

    /**
     * Extracts the file name without its extension from the given parameter
     *
     * @param filename the filename from which to remove the extension
     *
     * @return the file name without the extension if it has an extension, or the file name itself if it doesn't have an extension
     */
    String getFileNameWithoutExtension(String filename);

    /**
     * Extracts the file extension from the given filename
     *
     * @param filename the file name from which to extract the extension
     *
     * @return the file extension in lowercase, or an empty string if no extension is found
     */
    String getFileExtension(String filename);
}