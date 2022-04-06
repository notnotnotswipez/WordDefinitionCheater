import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    static List<String> wordsToDefine = new ArrayList<>();
    static String urlString = "https://api.dictionaryapi.dev/api/v2/entries/en";

    static Random random = new Random();

    static {
        wordsToDefine.add("coordinated");
        wordsToDefine.add("coordination");
        wordsToDefine.add("integration");
        wordsToDefine.add("control");
        wordsToDefine.add("nerve cells");
        wordsToDefine.add("nerve impulse");
        wordsToDefine.add("neuron");
        wordsToDefine.add("synapse");
        wordsToDefine.add("neurotransmitter");
        wordsToDefine.add("stimulus");
        wordsToDefine.add("response");
        wordsToDefine.add("nervous system");
        wordsToDefine.add("central nervous system");
        wordsToDefine.add("peripheral nervous system");
        wordsToDefine.add("sensory receptors");
        wordsToDefine.add("endocrine system");
        wordsToDefine.add("endocrine glands");
        wordsToDefine.add("hormones");
        wordsToDefine.add("special target cells");
        wordsToDefine.add("receptor molecules");
        wordsToDefine.add("hypothalamus");
        wordsToDefine.add("pituitary gland");
        wordsToDefine.add("adrenal gland");
        wordsToDefine.add("insulin");
        wordsToDefine.add("pancreas");
    }

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();

        for (String word : wordsToDefine) {
            String definition = getDefinition(word);
            sb.append(word).append(" - ").append(definition).append("\n");
            sb.append("\n ");
        }

        System.out.println(sb.toString());
    }

    private static String getDefinition(String word) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(urlString + "/" + word.toLowerCase().replace(" ", "%20")).openConnection();
            connection.setRequestMethod("GET");

            connection.connect();

            // Read the response
            StringBuilder response = new StringBuilder();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            JsonArray jsonObject = JsonParser.parseString(response.toString()).getAsJsonArray();
            JsonArray meanings = jsonObject.get(0).getAsJsonObject().getAsJsonArray("meanings");
            JsonArray definitions = meanings.get(random.nextInt(meanings.size())).getAsJsonObject().getAsJsonArray("definitions");

            return definitions.get(0).getAsJsonObject().get("definition").getAsString();
        }
        catch (Exception e) {
            // ignore
        }

        return "null";
    }
}
