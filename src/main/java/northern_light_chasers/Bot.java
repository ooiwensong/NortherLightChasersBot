package northern_light_chasers;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class Bot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;

    public Bot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {
        System.out.println(getWeatherInfo("Singapore"));
        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println("incoming update");
//            SendMessage message = SendMessage
//                    .builder()
//                    .chatId(update.getMessage().getChatId())
//                    .text(update.getMessage().getText())
//                    .build();
//
//            try {
//                telegramClient.execute(message);
//            } catch (TelegramApiException e) {
//                throw new RuntimeException(e);
//            }
        }
    }

    private String getWeatherInfo(String location) {
        String openWeatherToken = System.getenv("OPEN_WEATHER_TOKEN");
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", location, openWeatherToken);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String resData = response.body().string();
                JSONObject json = new JSONObject(resData);
                String weather = json.getJSONArray("weather").getJSONObject(0).getString("description");
                System.out.println(weather);
                return resData;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Response not available";
    }
}