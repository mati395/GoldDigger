# Gold Digger
### A simple discord bot which can search a random YouTube video or prnt.sc screenshot.
### Made with Java and Maven. 

## Prerequisites
[JDK 21](https://www.oracle.com/pl/java/technologies/downloads/ "JDK")

## How to launch bot

```
wget https://github.com/mati395/GoldDigger/releases/download/1.0.0/GoldDigger1.0.jar

java -jar GoldDigger1.0.jar
```

## Configuration
When you launch the application for the first time, it will create a Data folder and a Data.json file in it.

```
Add Discord API key and YouTube API key!
/<YOUR_REPOSITORIES_DIRECTORY>/GoldDigger/Data/Data.json

Cannot run bot! Please update your Discord token.
/<YOUR_REPOSITORIES_DIRECTORY>/GoldDigger/Data/Data.json
```

When you open Data.json you will see the following parameters

```
  "botToken": "",
  "youTubeAPIKey": "",
```

For creating new bot and generating a Token check here: [link](https://github.com/reactiflux/discord-irc/wiki/Creating-a-discord-bot-&-getting-a-token)

For making new project in the Google Developers Console and generating YouTube API Key check here: [link](https://developers.google.com/youtube/v3/getting-started?hl=en)

Add the token from Discord Developer Portal (Bot/Build-A-Bot) to "botToken", also add YouTube API key to "youTubeAPIKey".

After completing these steps you can use the command in your directory with jar:
```
java -jar GoldDigger1.0.jar
```

## Commands
```
/commands - Show all available commands
/randomvid - Show one random video from YouTube
/hardvid - Show one random video with .mp4 or .mov prefix
/imgs - Show 5 random prnt.sc screenshots
```
### Have fun!