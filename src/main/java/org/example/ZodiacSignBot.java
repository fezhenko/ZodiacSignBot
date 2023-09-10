package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.example.ZodiacSignUtils.determineZodiacSign;

public class ZodiacSignBot extends TelegramLongPollingBot {

    public static final String DETERMINE_MESSAGE = "Enter the date to determine your zodiac sign in format YYYY.MM.DD";
    public static final String START_MESSAGE = "/start";
    private static final String BOT_USERNAME = "ZodiacSignQualifierBot";
    public static final String DATE_PATTERN = "\\d{4}\\.\\d{2}\\.\\d{2}";

    public ZodiacSignBot(String botToken) {
        super(botToken);
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (!update.hasMessage()) {
            return;
        }

        Message message = update.getMessage();
        long chatId = message.getChatId();
        if (!message.hasText()) {
            return;
        }

        String inputText = message.getText();
        if (START_MESSAGE.equals(inputText)) {
            sendMessage(DETERMINE_MESSAGE, message.getChatId());
            return;
        }

        if (inputText.matches(DATE_PATTERN)) {
            try {
                sendMessage(determineZodiacSignFromDateString(inputText), chatId);
            } catch (Exception exception) {
                sendMessage(exception.getMessage(), chatId);
            }

        } else {
            sendMessage(DETERMINE_MESSAGE, chatId);
        }
    }

    private String determineZodiacSignFromDateString(String dateString) {
        try {
            int month = Integer.parseInt(dateString.substring(dateString.indexOf('.') + 1, dateString.indexOf('.') + 3));
            int day = Integer.parseInt(dateString.substring(dateString.indexOf('.') + 4, dateString.indexOf('.') + 6));
            return "Zodiac sign: %s".formatted(determineZodiacSign(month, day));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString);
        }
    }

    private void sendMessage(String message, long chatId) {
        SendMessage sendMessage = SendMessage.builder()
                .text(message)
                .chatId(chatId)
                .build();

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
