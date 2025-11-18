package test;

import exception.AlreadyExistingDirectory;
import model.Directory;
import model.MediaFile;
import service.MediaFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.implementation.DirectoryServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryServiceImplUnitTest {

    private MediaFileService mediaFileServiceStub;
    private DirectoryServiceImpl directoryService;

    @BeforeEach
    void setUp() {

        mediaFileServiceStub = new MediaFileService() {
            @Override
            public boolean isMediaFile(String filename) {
                return filename.contains(".");
            }

            @Override public MediaFile createMediaFile(String path) { return null; }
            @Override public void saveMediaFile(MediaFile mediaFile) {}
            @Override public void deleteMediaFile(MediaFile mediaFile) {}
            @Override public MediaFile getMediaFileByPath(String path) { return null; }
            @Override public List<MediaFile> getAllMediaFiles() { return null; }
            @Override public String getFileNameWithoutExtension(String filename) { return null; }
            @Override public String getFileExtension(String filename) { return null; }
        };

        directoryService = new DirectoryServiceImpl(mediaFileServiceStub);
    }

    @Test
    void testCreateDirectory() throws AlreadyExistingDirectory {

        String pathInput = "C:/Users/Alex";

        Directory result = directoryService.createDirectory(pathInput);

        assertNotNull(result);
        assertEquals("Alex", result.getName());
        assertEquals("C:/Users/", result.getPath());
    }

    @Test
    void testCreateDirectoryWithMediaFileChild() throws AlreadyExistingDirectory {

        String pathInput = "C:/Music/song.mp3";

        Directory result = directoryService.createDirectory(pathInput);

        assertEquals("Music", result.getName());
        assertEquals("C:/", result.getPath());
    }

    @Test
    void testSaveDirectory() throws AlreadyExistingDirectory {

        Directory d1 = new Directory();
        d1.setName("Test");
        d1.setPath("C:/");

        directoryService.saveDirectory(d1);

        Directory d2 = new Directory();
        d2.setName("Test");
        d2.setPath("C:/");

        assertThrows(AlreadyExistingDirectory.class, () -> {
            directoryService.saveDirectory(d2);
        });
    }
}