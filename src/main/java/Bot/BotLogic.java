package Bot;

import Data.DataLoader;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import Generator.*;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.nio.file.Path;
import java.nio.file.Paths;


public class BotLogic {
    public boolean isReady() {
        return isReady;
    }

    private void setReady(boolean ready) {
        isReady = ready;
    }

    private boolean isReady = false;

    public void launchBot(){
        Path root = Paths.get(".").normalize().toAbsolutePath();
        DataLoader dataLoader = new DataLoader();
        dataLoader.makeDirectory();
        if (DataLoader.loadData().getBotToken().isEmpty()){
            System.out.println("Cannot run bot! Please update your Discord token.");
            System.out.println(root +"/Data/Data.json");
            setReady(false);
            return;
        }
        if (DataLoader.loadData().getYouTubeAPIKey().isEmpty()) {
                System.out.println("Cannot run bot! Please update your YouTube API token.");
                System.out.println(root + "/Data/Data.json");
                setReady(false);
                return;
        }

        setReady(true);


    }
    public void startBot(){
        DataLoader dataLoader = new DataLoader();
        dataLoader.makeDirectory();
        RandomVideoSearcher randomVideoSearcher = new RandomVideoSearcher();
        VideoSearcher videoSearcher = new VideoSearcher();

        DiscordApi api = new DiscordApiBuilder()
                .setToken(DataLoader.loadData().getBotToken())
                .setAllIntents()
                .login()
                .join();

        SlashCommand allCommand = SlashCommand.with("commands", "Show all available commands")
                .createGlobal(api)
                .join();

        SlashCommand imgsCommand = SlashCommand.with("imgs", "Show 5 random prnt.sc screenshots")
                .createGlobal(api)
                .join();

        SlashCommand randomvidCommand = SlashCommand.with("randomvid", "Show one random video from YouTube")
                .createGlobal(api)
                .join();

        SlashCommand hardvidCommand = SlashCommand.with("hardvid", "Show one random video with img postfix or mov prefix from YouTube")
                .createGlobal(api)
                .join();

        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction commandInteraction = event.getSlashCommandInteraction();
            if (commandInteraction.getFullCommandName().equals("commands")){
                commandInteraction.createImmediateResponder().setContent("Here is available options: \n" +
                        "/imgs - Show 5 random prnt.sc screenshots \n" +
                        "/randomvid - Show one random video from YouTube\n" +
                        "/hardvid - Show one random video with img postfix or mov prefix from YouTube \n"
                ).respond();

            }
        });

        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction imgInteraction = event.getSlashCommandInteraction();
            if (imgInteraction.getFullCommandName().equals("imgs")){
                imgInteraction.createImmediateResponder().setContent(PictureGenerator.pictureUrl(5)).respond();
            }
        });

        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction randomVideoInteraction = event.getSlashCommandInteraction();
            if (randomVideoInteraction.getFullCommandName().equals("randomvid")){
                randomVideoInteraction.createImmediateResponder().setContent(videoSearcher.findRandomYouTubeVideoById()).respond();
            }
        });

        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction randomVideoInteraction = event.getSlashCommandInteraction();
            if (randomVideoInteraction.getFullCommandName().equals("hardvid")){
                randomVideoInteraction.createImmediateResponder().setContent(randomVideoSearcher.searchVideoWithPrePostfix()).respond();
            }
        });
    }
}