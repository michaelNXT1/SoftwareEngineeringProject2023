package UnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;

import BusinessLayer.*;
import ServiceLayer.DTOs.ProductDTO;
import junit.framework.TestCase;


import static org.junit.jupiter.api.Assertions.*;
public class ParseFileTest extends TestCase{
        private Market market;
        private String validFilePath;
        private String invalidFilePath;

        @BeforeEach
        public void setUp() {
            validFilePath = "valid_commands.txt";       // Path to a file with valid commands
            invalidFilePath = "invalid_commands.txt";   // Path to a file with invalid commands
            market = new Market(null, true);            // Create a Market instance in test mode
        }

        @Test
        public void testParseFile_ValidFile() {
            File validFile = new File(validFilePath);
            assertTrue(validFile.exists());

            assertDoesNotThrow(() -> market.parseFile(validFilePath));

        }

        @Test
        public void testParseFile_InvalidFile() {
            File invalidFile = new File(invalidFilePath);
            assertTrue(invalidFile.exists());  // Make sure the invalid commands file exists

            assertThrows(RuntimeException.class, () -> market.parseFile(invalidFilePath));

        }

        @Test
        public void testParseFile_FileNotFound() {
            String nonExistingFilePath = "non_existing_file.txt";

            assertThrows(RuntimeException.class, () -> market.parseFile(nonExistingFilePath));
        }
    }
