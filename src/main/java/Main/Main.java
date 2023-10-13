package Main;

import Bot.BotLogic;

public class Main {

    public static void main(String[] args) {
        BotLogic botLogic = new BotLogic();
        botLogic.launchBot();

        if (botLogic.isReady()) botLogic.startBot();
    }
}