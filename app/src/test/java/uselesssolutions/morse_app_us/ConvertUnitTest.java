package uselesssolutions.morse_app_us;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ConvertUnitTest {

    MorseTextClass morse = new MorseTextClass();
    ThirdLook test = new ThirdLook();



    @Test
    public void textToMorse() {

        String actual = MorseCode.alphaToMorse("d");

        String expected ="- . .       ";

        assertEquals(expected, actual);
    }

    @Test
    public void morseToText(){
        String a = "./";
        String actual = morse.morseToText(a);

        String expected = "e";
        assertEquals(expected, actual);

    }

    @Test
    public void displayString(){
        String actual = test.makeDisplayString("---");

        String expected = " _  _  _ ";
        assertEquals(expected,actual);

    }

    @Test
    public void deleteString(){
        String actual = test.deleteForMethod("---");

        String expected = "--";
        assertEquals(expected,actual);

    }

}