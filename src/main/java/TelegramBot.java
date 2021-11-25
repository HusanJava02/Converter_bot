import lombok.Data;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

@Data
public class TelegramBot extends TelegramLongPollingBot {
    private Long adminId;
    int integerLastChar = -1;
    int integerLastCharLeft = -1;
    public static int countsOfUsingMethod = 0;
    List<JsonObjectNBU> objectNBUList;
    CallbackQuery callbackQuery;
    boolean[] isPressedRightKeyboard = new boolean[5];
    boolean[] isPressedLeftKeyboard = new boolean[5];
    @Override
    public String getBotUsername() {
        return "@ConverterUzstoUsd_bot";
    }

    @Override
    public String getBotToken() {
        return "1766015177:AAH9rmubY9R_vs4-2wGR6k7oaJpF6QgAzjI";
    }
    String[] phraseExchange = new String[]{"AQSH dollari","Evro","Rossiya rubli","Anglya funt sterlingi","Yaponiya iyenasi"};
    String[] codeOfExchange = new String[]{"USD","EUR","RUB","GBP","JPY"};
    String[] flags = new String[]{"\uD83C\uDDFA\uD83C\uDDF8","\uD83C\uDDEA\uD83C\uDDF8","\uD83C\uDDF7\uD83C\uDDFA","\uD83C\uDDEC\uD83C\uDDEA","\uD83C\uDDEF\uD83C\uDDF5"};
    Long editedMessageId;




    @Override
    public void onUpdateReceived(Update update) {

        System.out.println("salom in update");
        String lastKeyword = "";
        Converter converter = new Converter();
        objectNBUList = converter.toObject();
        Message message = update.getMessage();
        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (callbackQuery != null){
            this.callbackQuery = callbackQuery;
            Character callBackQueryChar = callbackQuery.getData().charAt(update.getCallbackQuery().getData().length()-1);
            String callBackQueryKeyword = callbackQuery.getData().substring(update.getCallbackQuery().getData().length()-2);
            if (Character.isDigit(callBackQueryChar)){
                integerLastChar = Integer.parseInt(String.valueOf(callBackQueryChar));

                if (callBackQueryKeyword.endsWith("z"+integerLastChar))
                {

                    isPressedLeftKeyboard[integerLastChar] = true;

                }

            }
            else if (Character.isLetter(callBackQueryKeyword.charAt(callBackQueryKeyword.length()-1)) && !callbackQuery.getData().equals("Next")){
                lastKeyword = callBackQueryKeyword;

            }

        }

        if (update.hasCallbackQuery()){
            boolean equals = false;
            String rightKeyboardCall = update.getCallbackQuery().getData();
            if (Character.isDigit(rightKeyboardCall.charAt(rightKeyboardCall.length()-1))){
                integerLastCharLeft = Integer.parseInt(String.valueOf(rightKeyboardCall.charAt(rightKeyboardCall.length()-1)));
                Arrays.fill(isPressedRightKeyboard,false);
            }
            rightKeyboardCall = rightKeyboardCall.substring(0,rightKeyboardCall.length()-1);


            try {

                equals = update.getCallbackQuery().getData().equals(objectNBUList.get(integerLastChar).getCcyNm_UZ() + "uz"+integerLastChar);


            }catch (IndexOutOfBoundsException e){


                sendMessage("❗️Tizimda qandaydir xatolik yuz berdi, Iltimos qaytadan botga start bering❗️",message.getChatId());

            }
            //MALUMOT VALYUTANING NOMI VA OXIRIDA "uz" SOZI BILAN KELADI MASALAN "AQSH DOLLARIUZ"
            if (equals){


                sendMessageEdited("So'mdagi mablag'ingizni kiriting \uD83C\uDDFA\uD83C\uDDFF \uD83D\uDC47 \uD83D\uDC47 "
                        +"va men uni "+phraseExchange[integerLastChar]+"ga \uD83D\uDCB5 aylantirib beraman"+System.lineSeparator()+"" +
                        "Usul : UZS  \uD83D\uDD04 "+codeOfExchange[integerLastChar]+System.lineSeparator() +
                        "Format: 4 000 yoki 4000",callbackQuery.getMessage().getChatId());
                isPressedRightKeyboard[integerLastChar] = true;


            }else if (callbackQuery.getData().equals("Back")){

            }else if (update.getCallbackQuery().getData().equals("Next")){

                SendMessage sendEdited = CurrencyShow.showCurrencies(objectNBUList, new MessageCallbackquery(message, callbackQuery));
                sendMessageEdited(sendEdited.getText(),callbackQuery.getMessage().getChatId(),CurrencyShow.nextButton());
            }else if (rightKeyboardCall.equals(objectNBUList.get(integerLastCharLeft).getCcyNm_UZ())){

                sendMessageEdited(objectNBUList.get(integerLastCharLeft).getCcyNm_UZ()+" dagi mablag'ingizni kiriting "+System.lineSeparator() +
                        "va men uni so'mga aylantirib beraman"+System.lineSeparator()+"" +
                        "Usul : "+codeOfExchange[integerLastCharLeft]+" ➡️ UZS"+System.lineSeparator() +
                        "Format : 4 000 yoki 4000",callbackQuery.getMessage().getChatId());
                isPressedLeftKeyboard[integerLastCharLeft] = true;


            }
        }
        if (update.hasMessage() ) {

            if (update.getMessage().hasText()) {
                if (message.getText().equals("/955ba4e8-f800-45e7-b54d-57917f7a3973")){
                    adminId = message.getChatId();
                    sendMessage("You are Admin now! \nReklama Postini jonating !",message.getChatId());
                }
                if (message.getText().equals("/start")) {
                    Arrays.fill(isPressedLeftKeyboard,false);
                    Arrays.fill(isPressedRightKeyboard,false);
                   if (adminId!=null){
                       if (adminId.equals(message.getChatId())){

                           adminId = 0l;
                       }
                   }
                    sendMessage("Assalomu Alaykum \uD83D\uDC4B "+System.lineSeparator() + update.getMessage().getFrom().getFirstName() +
                            System.lineSeparator() + "Men bilan oson ayirboshlang! ♻️"+System.lineSeparator()+System.lineSeparator()+"@ConverterUzstoUsd_bot" , message.getChatId(),setMarkup());


                    InlineKeyboardMarkup markup = keyboard();
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Tanlang \uD83D\uDC47\uD83D\uDC47");
                    sendMessage.setChatId(message.getChatId());
                    sendMessage.setReplyMarkup(markup);

                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }else if (message.getText().equals("Tanlash ✔️") || message.getText().equals("/other")){
                    InlineKeyboardMarkup inlineKeyboardMarkup = keyboard();
                    sendMessage("Tanlang",message.getChatId(),inlineKeyboardMarkup);
                }else if (message.getText().equals("Valyutalar \uD83D\uDCC8")){
                    SendMessage sendingCurrencyShow = CurrencyShow.showCurrencies(objectNBUList, new MessageCallbackquery(message,callbackQuery));
                    try {
                        execute(sendingCurrencyShow);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                String[] s = message.getText().split(" ");
                String textVal = "";
                for (String s1 : s) {
                    s1 = s1.trim();

                    textVal+=s1;
                }

                if (textVal.matches("[0-9]+") && isPressedRightKeyboard[integerLastChar]){
                    System.out.println("1");
                    System.out.println("Left"+Arrays.toString(isPressedLeftKeyboard));
                    System.out.println("Right"+Arrays.toString(isPressedRightKeyboard));

                    String value = message.getText();
                    try {



                        double calculated = calculateFromUzs(textVal, objectNBUList.get(integerLastChar).getRate(), message.getChatId());

                        sendMessage("\uD83D\uDD04 Operatsiya : "+"UZS"+" to "+codeOfExchange[integerLastChar]+"" +
                                ""+System.lineSeparator()+"\uD83C\uDDFA\uD83C\uDDFF "+String.format("%,.2f",Double.parseDouble(textVal))+"  so'm  =  "+String.format("%,.3f",calculated)+" "+
                                codeOfExchange[integerLastChar] +System.lineSeparator()+
                                "♻️Oxirgi yangilanish : "+objectNBUList.get(integerLastChar).getDate(),message.getChatId());

                        sendMessage("Qaytadan tanlash uchun , Tanlash buyrug'ini jo'nating",message.getChatId());


                    }catch (NumberFormatException e){
                        sendMessage("Raqam noto'g'ri formatda kiritildi, Iltimos tekshirip korib qaytadan kiriting !",message.getChatId());
                    }

                }else if (textVal.matches("[0-9]+") && isPressedLeftKeyboard[integerLastCharLeft]){


                    double result = calculateToUzs(textVal, objectNBUList.get(integerLastCharLeft).getRate());
                    sendMessage("\uD83D\uDD04 Operatsiya : "+codeOfExchange[integerLastCharLeft]+" to UZS" +
                            ""+System.lineSeparator()+flags[integerLastCharLeft]+String.format("%,.2f",Double.parseDouble(textVal))+"  "+codeOfExchange[integerLastChar]+"  =  "+String.format("%,.3f",result)+
                            " UZS \uD83C\uDDFA\uD83C\uDDFF" +System.lineSeparator()+
                            "♻️Oxirgi yangilanish : "+objectNBUList.get(integerLastChar).getDate(),message.getChatId());

                }
                else if (isPressedRightKeyboard[integerLastChar] && !message.getText().equals("Tanlash ✔️") && !message.getText().equals("Valyutalar \uD83D\uDCC8") && !message.getText().equals("/other") && !message.getText().equals("/users")){
                    sendMessage("Tanlash buyrug'i orqali qayta urinib koring",message.getChatId());


                }


            }

        }
    }


    public void sendMessage(String sendingText,Update update) {
        SendMessage sendMessage = new SendMessage()
                .setText(sendingText)
                .setChatId(update.getMessage().getChatId())
                .setParseMode(ParseMode.MARKDOWN);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
    public void sendMessage(String sendingText,Long chatId) {
        SendMessage sendMessage = new SendMessage()
                .setText(sendingText)
                .setChatId(chatId)
                .setParseMode(ParseMode.MARKDOWN);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
    public void sendMessage(String sendingText,Long chatId,ReplyKeyboardMarkup markup) {
        SendMessage sendMessage = new SendMessage()
                .setText(sendingText)
                .setChatId(chatId)
                .setReplyMarkup(markup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
    public void sendMessage(String sendingText,Long chatId,InlineKeyboardMarkup markup) {
        SendMessage sendMessage = new SendMessage()
                .setText(sendingText)
                .setChatId(chatId)
                .setParseMode(ParseMode.MARKDOWN)
                .setReplyMarkup(markup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
    public void sendMessageEdited(String sendingText,Long chatId) {

        EditMessageText editMessageText = new EditMessageText()
                .setText(sendingText)
                .setMessageId(callbackQuery.getMessage().getMessageId())
                .setChatId(chatId);

        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
    public void sendMessageEdited(String sendingText,Long chatId,InlineKeyboardMarkup markup) {

        EditMessageText editMessageText = new EditMessageText()
                .setText(sendingText)
                .setMessageId(callbackQuery.getMessage().getMessageId())
                .setChatId(chatId)
                .setReplyMarkup(markup);
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
    public InlineKeyboardMarkup keyboard(){
        InlineKeyboardButton button;
        InlineKeyboardButton button2;
        InlineKeyboardButton button3;
        List<InlineKeyboardButton> row = new ArrayList<>();
        List<List<InlineKeyboardButton>> colum = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            button = new InlineKeyboardButton();
            button.setText(objectNBUList.get(i).getCcyNm_UZ());
            button.setCallbackData(objectNBUList.get(i).getCcyNm_UZ()+i);
            row.add(button);
            button2 = new InlineKeyboardButton();
            button2.setText("\uD83C\uDDFA\uD83C\uDDFF UZS");
            button2.setCallbackData(objectNBUList.get(i).getCcyNm_UZ()+"uz"+i);
            row.add(button2);
            colum.add(row);
            row = new ArrayList<>();
        }
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(colum);

        return markup;

    }
    public double calculateFromUzs(String value, String rate,Long chatId) throws NumberFormatException{
        Double val = 0.0;
        val = Double.parseDouble(value)/Double.parseDouble(rate);
        return val;
    }
    public double calculateToUzs(String value, String rate) throws NumberFormatException{
        Double val = Double.parseDouble(value)*Double.parseDouble(rate);
        return val;
    }
    public ReplyKeyboardMarkup setKeyboards(KeyboardRow... rows){
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> list = new ArrayList<>(Arrays.asList(rows));
        markup.setKeyboard(list);
        return markup;
    }
    public ReplyKeyboardMarkup setMarkup(){
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add("Tanlash ✔️");
        keyboardRow1.add("Valyutalar \uD83D\uDCC8");

        ReplyKeyboardMarkup markup1 = setKeyboards(keyboardRow1);
        markup1.setResizeKeyboard(true);
        return markup1;
    }




}
