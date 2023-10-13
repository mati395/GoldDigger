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
import java.util.Random;
import java.util.Arrays;
import java.util.List;

public class RandomVideoSearcher {
    private final String YOUTUBE_URL_PREFIX = "https://www.youtube.com/watch?v=";
    private static final List<String> prefix = Arrays.asList("IMG ", "IMG_", "IMG-", "DSC "," ");
    private static final List<String> postfix = Arrays.asList(" MOV", ".MOV", " .MOV"," .3GP"," .WMV"," .MP4"," ");

    public String searchVideoWithPrePostfix() {
        try {

            NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            HttpRequestInitializer httpRequestInitializer = request -> {
            };

            YouTube youtube = new YouTube.Builder(httpTransport, jsonFactory, httpRequestInitializer)
                    .setApplicationName("GoldDigger")
                    .build();
            String randomQuery = getRandomQuery();
            List<String> videos = searchVideos(youtube, randomQuery, 5);
            String randomVideo = getRandomVideo(videos);

            return YOUTUBE_URL_PREFIX + randomVideo;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getRandomQuery() {
        Random random = new Random();
        String randomPrefix = prefix.get(random.nextInt(prefix.size()));
        String randomPostfix = postfix.get(random.nextInt(postfix.size()));
        int randomInt = 999 + random.nextInt(9000);
        return randomPrefix + randomInt + randomPostfix;
    }

    private static List<String> searchVideos(YouTube youtube, String query, long maxResults) throws Exception {
        YouTube.Search.List searchListRequest = youtube.search().list("id,snippet");
        searchListRequest.setKey(DataLoader.loadData().getYouTubeAPIKey());
        searchListRequest.setQ(query);
        searchListRequest.setType("video");
        searchListRequest.setMaxResults(maxResults);

        SearchListResponse searchListResponse = searchListRequest.execute();
        List<SearchResult> searchResults = searchListResponse.getItems();

        List<String> videos = new ArrayList<>();
        for (SearchResult searchResult : searchResults) {
            if ("youtube#video".equals(searchResult.getId().getKind())) {
                videos.add(searchResult.getId().getVideoId());
            }
        }
        return videos;
    }

    private static String getRandomVideo(List<String> videos) {
        Random random = new Random();
        int randomIndex = random.nextInt(videos.size());
        return videos.get(randomIndex);
    }
}