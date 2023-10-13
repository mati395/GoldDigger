package Data;

import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


public class DataLoader {

    public static boolean doesDirectoryExists(String dir) {
        File tempFile = new File(dir);
        return tempFile.exists() && tempFile.isDirectory();
    }

    public static boolean doesFileExists(String dir) {
        File tempFile = new File(dir);
        return tempFile.exists();
    }

    public File getJson() {
        Path root = Paths.get(".").normalize().toAbsolutePath();
        File cleanJson = new File(root + "/Data/Data.json");
        try {
            cleanJson.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL("https://pastebin.com/raw/V5vJtP4T");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line + "\n");
                }
                String content = result.toString();

                FileWriter fileWriter = new FileWriter(cleanJson);
                fileWriter.write(content);
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cleanJson;
    }

    public void makeDirectory() {
        Path root = Paths.get(".").normalize().toAbsolutePath();
        File dataPath = new File(root + "/Data");
        File dataFile = new File(root + "/Data/Data.json");
        if (doesDirectoryExists(dataPath.toString()) == false) {
            dataPath.mkdir();
            if (dataPath.exists()) {
                getJson();
                System.out.println("Add Discord API key and YouTube API key!");
                System.out.println(root + "/Data/Data.json");
            } else {
                System.out.println(root);
                System.out.println("Can't make new directory");
            }
        } else {
            if (doesFileExists(dataFile.toString()) == false) {
                getJson();
            } else loadData();
        }
    }

    public static DataContainer loadData() {
        Path root = Paths.get(".").normalize().toAbsolutePath();

        try (FileReader reader = new FileReader(root + "/Data/Data.json")) {

            Gson gson = new Gson();
            DataContainer dataContainer = gson.fromJson(reader, DataContainer.class);

            return dataContainer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}