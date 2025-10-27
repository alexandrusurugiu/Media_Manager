package service.implementation;

import exception.AlreadyExistingFile;
import model.MediaFile;
import service.MediaFileService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MediaFileServiceImpl implements MediaFileService {

    public final List<MediaFile> mediaFileList = new ArrayList<>();

    @Override
    public MediaFile createMediaFile(String path) {

        MediaFile mediaFile = new MediaFile();

        String[] components = path.split("/");
        String lastComponent = components[components.length - 1];
        if (isMediaFile(lastComponent)) {
            String fileName = getFileNameWithoutExtension(lastComponent);
            StringBuilder filePathStringBuilder = new StringBuilder();

            for (int i = 0; i < components.length - 1; i++) {
                filePathStringBuilder.append(components[i]).append("/");
            }

            String filePath = filePathStringBuilder.toString();
            String extension = getFileExtension(lastComponent);
            Date date = new Date();

            mediaFile.setName(fileName);
            mediaFile.setPath(filePath);
            mediaFile.setLastModified(date);
            mediaFile.setFileType(extension);

            if (mediaFile.getName() != null) {
                saveMediaFile(mediaFile);
            }
        }

        return mediaFile;
    }

    @Override
    public void saveMediaFile(MediaFile mediaFile) {

        for (MediaFile currentMediaFile : mediaFileList) {
            if (mediaFile.getName().equals(currentMediaFile.getName()) && mediaFile.getFileType().equals(currentMediaFile.getFileType())) {
                throw new AlreadyExistingFile("Fisierul " + mediaFile.getName() + " cu extensia " + mediaFile.getFileType() + " exista deja in path-ul " + mediaFile.getPath());
            }
        }

        mediaFileList.add(mediaFile);
    }

    @Override
    public void deleteMediaFile(MediaFile mediaFile) {

        mediaFileList.remove(mediaFile);
    }

    @Override
    public MediaFile getMediaFileByPath(String path) {

        for (MediaFile mediaFile : mediaFileList) {
            if (mediaFile.getPath().equals(path)) {
                return mediaFile;
            }
        }

        return null;
    }

    @Override
    public List<MediaFile> getAllMediaFiles() {

        return mediaFileList;
    }

    @Override
    public boolean isMediaFile(String filename) {

        if (filename == null || !filename.contains(".")) {
            return false;
        }

        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        return extension.equals("mp3") || extension.equals("wav") || extension.equals("jpg") || extension.equals("png") || extension.equals("mp4") || extension.equals("jpeg");
    }

    @Override
    public String getFileNameWithoutExtension(String filename) {

        int dotIndex = filename.lastIndexOf('.');
        return dotIndex > 0 ? filename.substring(0, dotIndex) : filename;
    }

    @Override
    public String getFileExtension(String filename) {

        int dotIndex = filename.lastIndexOf('.');
        return dotIndex > 0 ? filename.substring(dotIndex + 1) : "";
    }
}