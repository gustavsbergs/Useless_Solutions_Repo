package uselesssolutions.morse_app_us;

import java.util.HashMap;

public class MorseCode {

    static String[] ALPHA = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
            "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "!", ",", "?",
            ".", "'" };
    static String[] MORSE = { ". -  ", "- . . .  ", "- . - .  ", "- . .  ", ".  ", ". . - .  ", "- - .  ", ". . . .  ", ". .  ", ". - - -  ", "- . -  ", ". - . .  ",
            "- -  ", "- .  ", "- - -  ", ". - - .  ", "- - . -  ", ". - .  ", ". . .  ", "-  ", ". . -  ", ". . . -  ", ". - -  ", "- . . -  ", "- . - -  ", "- - . .  ", ". - - - -  ",
            ". . - - -", ". . . - -", ". . . .-", ". . . . .", "- . . . .", "- - . . .", "- - - . .", "- - - -.", "- - - - -", "- . - . - -", "- - . . - -",
            ". . - - . .  ", ". - . - . -  ", ". - - - - .  ", };

    public static HashMap<String, String> TEXT_TO_MORSE = new HashMap<>();


    static {
        for (int i = 0; i < ALPHA.length  &&  i < MORSE.length; i++) {
            TEXT_TO_MORSE.put(ALPHA[i], MORSE[i]);
        }
    }

    public static String alphaToMorse(String englishCode) {
        StringBuilder builder = new StringBuilder();
        String[] words = englishCode.trim().split(" ");
        System.out.println(words);
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                String morse = TEXT_TO_MORSE.get(word.substring(i, i + 1).toLowerCase());
                builder.append(morse).append(" ");
                System.out.println("INSIDE: " +morse);
            }
            System.out.println("OUTSIDE: " +builder);
            builder.append("    ");
        }

        return builder.toString();
    }

}
