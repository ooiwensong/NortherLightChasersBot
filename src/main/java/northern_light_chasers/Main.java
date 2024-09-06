package northern_light_chasers;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

public class Main {
    public static void main(String[] args) {
        String botToken = System.getenv("BOT_TOKEN");

        try (TelegramBotsLongPollingApplication botsApp = new TelegramBotsLongPollingApplication()) {
            botsApp.registerBot(botToken, new Bot(botToken));
            System.out.println("Bot connected!");
            Thread.currentThread().join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}