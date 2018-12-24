package name.mizunotlt.eruditkurs;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static java.net.HttpURLConnection.HTTP_OK;

interface WordCheckerInterface {
    String KEY = "dict.1.1.20181002T210630Z.48eeb6b8a8579498.d722e12836ce5ecfbd5ff8822c5bfd2088b1723d";
    String COMMAND = "lookup";
    String LANG = "ru-ru";
    String urlApi = "https://dictionary.yandex.net/api/v1/dicservice.json/";

    boolean checkCorrectWord(JSONObject jsobj);
}
class WordChecker implements WordCheckerInterface {

    private String word;

    WordChecker(String word){
        this.word = word;
    }
    private  final JSONParser jsonParser = new JSONParser();


    public URL buildUrl() throws MalformedURLException {
        String str = urlApi +
                COMMAND +
                "?key=" +
                KEY +
                "&lang=" +
                LANG +
                "&text=" +
                this.word;
        return new URL(str);
    }

    public JSONObject connectYandexDicApi(URL forApi) throws IOException {
        JSONObject jsobj = new JSONObject();
        URL Api = forApi;
        HttpsURLConnection conn = (HttpsURLConnection) Api.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        if (conn.getResponseCode() == HTTP_OK) {
            try {
                try {
                    jsobj = (JSONObject) jsonParser.parse(in.readLine());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                return null;
            }
        } else {
            return null;
        }
        return jsobj;
    }

    @Override
    public boolean checkCorrectWord(JSONObject jsobj) {
        boolean result = false;
        JSONArray jsArray = (JSONArray)jsobj.get("def");
        if (jsArray.size() == 0)
            return false;
        JSONObject obj = (JSONObject) jsArray.get(0);
        Object text = obj.get("text");
        String pos = obj.get("pos").toString();
        if ((text !=  null) && (pos.toLowerCase().equals("noun"))){
            result = true;
        }
        return result;
    }
}
