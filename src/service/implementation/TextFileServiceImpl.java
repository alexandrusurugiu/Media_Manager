package service.implementation;

import exceptions.FilesLoadingException;
import exceptions.FilesSavingException;
import model.Directory;
import model.MediaFile;
import service.MediaFileService;
import service.TextFileService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextFileServiceImpl implements TextFileService {

    private static final String PATHS_FILE = "src//resources/paths.txt";

    private List<Directory> directories = new ArrayList<>();

    private MediaFileService mediaFileService;

    public TextFileServiceImpl(MediaFileServiceImpl mediaFileServiceImpl) {
        this.mediaFileService = mediaFileServiceImpl;
    }

    public List<Directory> getDirectories() {
        return directories;
    }

    public void setDirectories(List<Directory> directories) {
        this.directories = directories;
    }

    public List<String> loadFilesFromTextFile() throws FilesLoadingException {
        List<String> pathLinesToBeDisplayed = new ArrayList<>();
        Map<String, Directory> directoryMap = new HashMap<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(PATHS_FILE))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                pathLinesToBeDisplayed.add(line);
                String[] pathComponents = line.split("/");

                if (pathComponents.length == 0) {
                    continue;
                }

                StringBuilder currentPath = new StringBuilder();
                List<Directory> directoriesInCurrentPath = new ArrayList<>();

                for (int i = 0; i < pathComponents.length - 1; i++) {
                    String component = pathComponents[i];
                    if (component == null || component.isEmpty()) {
                        continue;
                    }

                    currentPath.append(component).append('/');
                    String dirPath = currentPath.toString();

                    Directory directory = directoryMap.get(dirPath);
                    if (directory == null) {
                        directory = new Directory();
                        directory.setName(component);
                        directory.setPath(dirPath);
                        directoryMap.put(dirPath, directory);
                        directories.add(directory);
                    }
                    directoriesInCurrentPath.add(directory);
                }

                MediaFile mediaFile = mediaFileService.createMediaFile(line);
                if (mediaFile != null && mediaFile.getName() != null) {
                    if (!directoriesInCurrentPath.isEmpty()) {
                        Directory parentDirectory = directoriesInCurrentPath.get(directoriesInCurrentPath.size() - 1);
                        parentDirectory.addMediaFile(mediaFile);
                    }
                }
            }
        } catch (IOException e) {
            throw new FilesLoadingException("Nu s-au putut incarca path-urile din fisierul text!");
        }
        return pathLinesToBeDisplayed;
    }

    @Override
    public void saveFilesToTextFile(List<MediaFile> mediaFiles) throws FilesSavingException {
        List<String> paths = new ArrayList<>();
        for (MediaFile mediaFile : mediaFiles) {
            StringBuilder mediaFilesPaths = new StringBuilder();
            String path = mediaFile.getPath() + mediaFile.getName() + '.' + mediaFile.getFileType();
            mediaFilesPaths.append(path);

            paths.add(mediaFilesPaths.toString());
        }

        try (FileWriter fileWriter = new FileWriter(PATHS_FILE, false)) {
            for (String path : paths) {
                fileWriter.write(path + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new FilesSavingException("Nu s-au putut salva noile path-uri in fisierul text!");
        }
    }
}