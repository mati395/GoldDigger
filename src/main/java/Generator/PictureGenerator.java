package Generator;

import java.util.Random;

public class PictureGenerator {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 6;

    public static String generateRandomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
    public static String pictureUrl(){
        return ("https://prnt.sc/" + generateRandomString());
    }

    public static String pictureUrl(int amount){
        String[] urls = new String[amount];
        String result = new String();
        for (int i = 0; i < amount; i++){
            urls[i] = pictureUrl() + " ";
        }
        for(int i = 0; i < urls.length; i++){
            result += urls[i];
        }
        return result;
    }
}