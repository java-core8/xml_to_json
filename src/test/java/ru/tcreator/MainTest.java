package ru.tcreator;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Main class")
public class MainTest {
    final static String resourcePath = Main.resourcePath;


    @Test
    @DisplayName("parse XML method throws FileNotFoundException ")
    void testParseXMLMethod() {
        Exception exception = assertThrows(
                FileNotFoundException.class, () -> Main.parseXML(resourcePath + "data.x")
        );
        assertEquals("файл не найден", exception.getMessage());
    }

    @Test
    @DisplayName("parse XML method not throws FileNotFoundException ")
    void testParseXMLMethodNotThrow() {
        assertDoesNotThrow(() -> Main.parseXML(resourcePath + "data.xml"));
    }

    @Test
    @DisplayName("correct path to resourсe directory")
    void testPath() {
        String path = "src/main/resources/";
        assertEquals(path, resourcePath);
    }

    @Test
    @DisplayName("WriteStringToFile method  doesn't throws exception")
    void testWriteStringToFile() {
        String jsonString = "{asdad:sdasd}";
        String filename = "output.json";
        assertDoesNotThrow(
                () -> Main.writeStringToFile(jsonString, filename, resourcePath)
        );
        File file = new File(resourcePath, filename);
        file.delete();
    }

    @Test
    @DisplayName("file .json is created?")
    void isCreationJsonFile() throws Exception {
        String path = "src/main/resources/";
        String jsonString = "{asdad:sdasd}";
        String filename = "output.json";
        Main.writeStringToFile(jsonString, filename, path);
        File file = new File(path, filename);
        assertTrue(file.exists());
        file.delete();
    }
}
