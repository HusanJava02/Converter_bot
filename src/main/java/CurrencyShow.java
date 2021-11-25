import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CurrencyShow {
    private static SendMessage sendMessage = new SendMessage();
    private static int count = 0;
    public static SendMessage showCurrencies(List<JsonObjectNBU> jsonObjectNBUList, MessageCallbackquery twoObject){
        if (twoObject.getMessage() == null)
            sendMessage.setChatId(twoObject.getCallbackQuery().getMessage().getChatId());
        else if (twoObject.getCallbackQuery() == null)
            sendMessage.setChatId(twoObject.getMessage().getChatId());


            sendMessage.setText(
                        "Rate : "+jsonObjectNBUList.get(count).getNominal()+" "+jsonObjectNBUList.get(count).getCcyNm_UZ()+" = "+jsonObjectNBUList.get(count++).getRate()+" UZS "+System.lineSeparator()+
                        "Rate : "+jsonObjectNBUList.get(count).getNominal()+" "+jsonObjectNBUList.get(count).getCcyNm_UZ()+" = "+jsonObjectNBUList.get(count++).getRate()+" UZS "+System.lineSeparator()+
                        "Rate : "+jsonObjectNBUList.get(count).getNominal()+" "+jsonObjectNBUList.get(count).getCcyNm_UZ()+" = "+jsonObjectNBUList.get(count++).getRate()+" UZS "+System.lineSeparator()+
                        "Rate : "+jsonObjectNBUList.get(count).getNominal()+" "+jsonObjectNBUList.get(count).getCcyNm_UZ()+" = "+jsonObjectNBUList.get(count++).getRate()+" UZS "+System.lineSeparator()+
                        "Rate : "+jsonObjectNBUList.get(count).getNominal()+" "+jsonObjectNBUList.get(count).getCcyNm_UZ()+" = "+jsonObjectNBUList.get(count++).getRate()+" UZS "+System.lineSeparator()

                );
            if (count >= 69) count = 0;
            sendMessage.setReplyMarkup(nextButton());
            return sendMessage;



    }
    public static InlineKeyboardMarkup nextButton(){
        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
        InlineKeyboardButton buttonNext = new InlineKeyboardButton();
        buttonNext.setText("Keyingi ➡️");
        buttonNext.setCallbackData("Next");
        inlineKeyboardButtons.add(buttonNext);
        List<List<InlineKeyboardButton>> list = new LinkedList<>();
        list.add(inlineKeyboardButtons);

        return new InlineKeyboardMarkup().setKeyboard(list);
    }
}
