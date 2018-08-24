package uselesssolutions.morse_app_us;

public class MorseTextClass {
    String [] morseArray = {"%", ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..",
            "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----",
            "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.", "-----", "-.-.--", "--..--",
            "..--..", ".-.-.-", ".----." };

    String [] textArray = {" ", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
            "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "!", ",", "?",
            ".", "'"};

    public String morseToText(String input) {
        String result = "";
        //  System.out.println(input);

        String temp = "";			// used to build seperate strings bound by the small spaces or "/" in our case
        int j; 					    // Index for the textArray and morseArray. Will be used to loop through these arrays and compare temp to each stored value
        for(int i = 0; i < input.length(); i++) {
            j = 0;
            if(input.charAt(i) != '/') {
                temp = temp + input.charAt(i);
            }else {
                //System.out.println("DONE: " + temp);
                while (j < morseArray.length - 1) {
                    if(temp.equals(morseArray[j])) {
                        result = result + textArray[j];
                    }
                    j++;
                }
                temp = "";			//resets the temp after a space has been found
            }
        }
        return result;
    }
}
