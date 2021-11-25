import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageCallbackquery {
    private Message message;
    private CallbackQuery callbackQuery;

}

