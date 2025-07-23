
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BotMain extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private static final String BOT_USERNAME = "your_bot_username_here";

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (message.equals("/start")) {
                sendPromoImage(chatId);
            }
        }
    }

    private void sendPromoImage(long chatId) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setCaption("🔥 Join These Channels for Daily Updates & Claim Your Bonus!");
        sendPhoto.setPhoto(new File("image.png"));

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            InlineKeyboardButton btn = new InlineKeyboardButton();
            btn.setText("Join Now " + i);
            btn.setUrl("https://t.me/your_channel_" + i);
            keyboard.add(Arrays.asList(btn));
        }

        InlineKeyboardButton claimButton = new InlineKeyboardButton();
        claimButton.setText("🎁 Claim Now");
        claimButton.setUrl("https://t.me/claim_channel");
        keyboard.add(Arrays.asList(claimButton));

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);
        sendPhoto.setReplyMarkup(markup);

        try {
            execute(sendPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new BotMain());
    }
}
