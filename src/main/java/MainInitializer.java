import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.BotSession;

public class MainInitializer {

    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();

        try {
            BotSession botSession = api.registerBot(new MainController());
        } catch (TelegramApiRequestException e) {
            String apiResponse = e.getApiResponse();
            System.out.println(apiResponse);
        }
    }
}
