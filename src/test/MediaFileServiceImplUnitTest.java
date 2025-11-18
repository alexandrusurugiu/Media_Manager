package test;

import exception.AlreadyExistingFile;
import model.MediaFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.implementation.MediaFileServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class MediaFileServiceImplUnitTest {

    private MediaFileServiceImpl mediaFileService;

    @BeforeEach
    void setUp() {
        mediaFileService = new MediaFileServiceImpl();
    }

    @Test
    void testValidMediaFileExtension() {
        assertTrue(mediaFileService.isMediaFile("melodie.mp3"));
        assertTrue(mediaFileService.isMediaFile("poza.jpg"));
        assertTrue(mediaFileService.isMediaFile("video.mp4"));
    }

    @Test
    void testInvalidMediaFileExtension() {
        assertFalse(mediaFileService.isMediaFile("document.txt"));
        assertFalse(mediaFileService.isMediaFile("executabil.exe"));
        assertFalse(mediaFileService.isMediaFile("folderFaraExtensie"));
    }

    @Test
    void testCreateMediaFile() throws AlreadyExistingFile {
        String path = "C:/Music/song.mp3";

        MediaFile result = mediaFileService.createMediaFile(path);

        assertNotNull(result);
        assertEquals("song", result.getName());
        assertEquals("mp3", result.getFileType());
        assertEquals("C:/Music/", result.getPath());

        assertEquals(1, mediaFileService.getAllMediaFiles().size());
    }

    @Test
    void testSaveMediaFile() throws AlreadyExistingFile {
        MediaFile file1 = new MediaFile();
        file1.setName("test");
        file1.setFileType("mp3");
        file1.setPath("C:/");

        mediaFileService.saveMediaFile(file1);

        MediaFile file2 = new MediaFile();
        file2.setName("test");
        file2.setFileType("mp3");
        file2.setPath("C:/");

        assertThrows(AlreadyExistingFile.class, () -> {
            mediaFileService.saveMediaFile(file2);
        });
    }

    @Test
    void testGetFileNameExtension() {
        assertEquals("fisier", mediaFileService.getFileNameWithoutExtension("fisier.txt"));
        assertEquals("fisier.complex", mediaFileService.getFileNameWithoutExtension("fisier.complex.jpg"));
        assertEquals("faraExtensie", mediaFileService.getFileNameWithoutExtension("faraExtensie"));
    }
}