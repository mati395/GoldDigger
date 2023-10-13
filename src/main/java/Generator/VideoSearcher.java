package Generator;

import Data.DataLoader;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VideoSearcher {

    private static final String YOUTUBE_URL_PREFIX = "https://www.youtube.com/watch?v=";

    public static String findRandomYouTubeVideoById() {
        try {
            NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            HttpRequestInitializer httpRequestInitializer = request -> {};

            YouTube youtube = new YouTube.Builder(httpTransport, jsonFactory, httpRequestInitializer)
                    .setApplicationName("GoldDigger")
                    .build();

            List<String> videos = searchRandomVideos(youtube, 1);
            return YOUTUBE_URL_PREFIX + getRandomVideo(videos);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> searchRandomVideos(YouTube youtube, long maxResults) throws Exception {

        YouTube.Search.List searchListRequest = youtube.search().list("id");
        searchListRequest.setKey(DataLoader.loadData().getYouTubeAPIKey());
        searchListRequest.setType("video");
        searchListRequest.setMaxResults(maxResults);
        searchListRequest.setOrder("date");

        List<String> videos = new ArrayList<>();

        String randomRegionCode = generateRandomRegionCode();
        searchListRequest.setRegionCode(randomRegionCode);

        SearchListResponse searchListResponse = searchListRequest.execute();
        List<SearchResult> searchResults = searchListResponse.getItems();

        for (SearchResult searchResult : searchResults) {
            if ("youtube#video".equals(searchResult.getId().getKind())) {
                videos.add(searchResult.getId().getVideoId());
            }
        }
        return videos;
    }

    private static String generateRandomRegionCode() {
        List<String> regionCodes = DataLoader.loadData().getRegionCodes();
        Random random = new Random();
        int randomIndex = random.nextInt(regionCodes.size());
        return regionCodes.get(randomIndex);
    }

    private static String getRandomVideo(List<String> videos) {
        Random random = new Random();
        int randomIndex = random.nextInt(videos.size());
        return videos.get(randomIndex);
    }
}
