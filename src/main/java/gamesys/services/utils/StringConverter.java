package gamesys.services.utils;

public class StringConverter {
    public static String process(String input){
        if (input == null){
            return null;
        }
        return input.toLowerCase().replaceAll("\n", "").replaceAll("\"", "");
    }
}
