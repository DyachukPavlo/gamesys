package gamesys.utils;

import gamesys.services.utils.StringConverter;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringConverterTest {

    @Test
    public void processNull() {
        assertNull(StringConverter.process(null));
    }

    @Test
    public void processEmptyString() {
        String input = "";
        assertEquals("", StringConverter.process(input));
    }

    @Test
    public void processStringWithUpperCaseAndQuotes() {
        String input = "\"Some stRiNg";
        String output = "some string";
        assertEquals(output, StringConverter.process(input));
    }
}