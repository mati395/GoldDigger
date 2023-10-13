package Data;

import java.util.List;

public class DataContainer {
    private String botToken;
    private String youTubeAPIKey;
    private List<String> regionCodes;
    public List<String> getRegionCodes() {
        return regionCodes;
    }

    public void setRegionCodes(List<String> regionCodes) {
        this.regionCodes = regionCodes;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public String getYouTubeAPIKey() {
        return youTubeAPIKey;
    }

    public void setYouTubeAPIKey(String youTubeAPIKey) {
        this.youTubeAPIKey = youTubeAPIKey;
    }
}