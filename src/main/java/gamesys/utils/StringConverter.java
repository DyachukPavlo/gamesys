package gamesys.utils;

public class StringConverter {
    public static String process(String input){
        return input.toLowerCase().replaceAll("\n", "").replaceAll("\"", "");
    }
}
