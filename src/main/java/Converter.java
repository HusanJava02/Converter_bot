import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Converter {

    String textJson = "";
    private JsonObjectNBU jsonObjectNBU;
    public List<JsonObjectNBU> toObject(){
        try {
            URL url = new URL("https://cbu.uz/uz/arkhiv-kursov-valyut/json/");
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            while ((line = reader.readLine())!=null){
                textJson += line;
            }

    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObjectNBU jsonObjectNBU = new JsonObjectNBU();

        List<JsonObjectNBU> objectNBUList = Arrays.asList( gson.fromJson(textJson, JsonObjectNBU[].class));
        return objectNBUList;


    }

}
