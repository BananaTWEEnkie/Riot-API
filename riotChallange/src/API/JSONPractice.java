/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.cedarsoftware.util.io.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.net.ssl.HttpsURLConnection;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import org.json.simple.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Twee
 */
public class JSONPractice {

    private final int jsonFileSize = 10000;
    private final int numberOfParticipants = 10;
    private final int itemSlots = 6;
    String url = "";

    ArrayList<Integer> itemList = new ArrayList<>();
    ArrayList<Integer> fullApList = new ArrayList<>();
    boolean tryAgain;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        JSONPractice http = new JSONPractice();

        System.out.println("Testing 1 - Send Http GET request");
        //http.sendGet();
        http.getMatch();

    }
    /*
     // HTTP GET request
     private void sendGet() throws Exception {

     Integer[] apIds = {1026, 1052, 1056,
     1058, 3001, 3003,
     3020, 3023, 3025,
     3027, 3040, 3041,
     3050, 3057, 3060,
     3089, 3092, 3098,
     3100, 3108, 3113,
     3115, 3116, 3124,
     3135, 3136, 3145,
     3146, 3151, 3152,
     3157, 3165, 3174,
     3191, 3285, 3303,
     3504, 3708, 3716,
     3720, 3724};

     //String url = "https://na.api.pvp.net/api/lol/na/v2.2/match/1852561658?api_key=222fb254-63ca-4338-9b7d-397694c1e099";

     URL obj = new URL(url);
     HttpURLConnection con = (HttpURLConnection) obj.openConnection();

     // optional default is GET
     con.setRequestMethod("GET");

     int responseCode = con.getResponseCode();
     System.out.println("\nSending 'GET' request to URL : " + url);
     System.out.println("Response Code : " + responseCode);

     BufferedReader in = new BufferedReader(
     new InputStreamReader(con.getInputStream()));
     String inputLine;
     StringBuffer response = new StringBuffer();

     while ((inputLine = in.readLine()) != null) {
     response.append(inputLine);
     }

     in.close();

     JSONObject jsonObject = new JSONObject(response.toString());

     for (int i = 0; i < numberOfParticipants; i++) {

     System.out.println("Participant " + (i + 1));

     JSONObject stats = jsonObject.getJSONArray("participants").getJSONObject(i).getJSONObject("stats");
     // String test = jsonObject.getJSONArray("participants").getJSONObject(i).getJSONObject("timeline").getString("lane");

     for (int n = 0; n < itemSlots; n++) {
     if (stats.getInt("item" + n) != 0) {
     itemList.add(stats.getInt("item" + n));
     }
     }

     // Sleep count came from request limit of 500 request per 10 minute.
     // Therefore, 1.2 sec per request is allowed.
     Thread.sleep(1200);
     }

     List<Integer> apIdsList = Arrays.asList(apIds);
     ArrayList<Integer> apList = new ArrayList<>();

     for (int itemId : itemList) {
     if (apIdsList.contains(itemId)) {
     apList.add(itemId);
     }
     }
        
     NumberFormat defaultFormat = NumberFormat.getPercentInstance();
     defaultFormat.setMinimumFractionDigits(1);
        
     for (int value : apIdsList) {
     double occurrences = Collections.frequency(apList, value);
     System.out.println(defaultFormat.format(occurrences / itemList.size()));
     }

     //print result
     //System.out.println(JsonWriter.formatJson(response.toString()));
     }
     */

    public void FileWriter(File file) throws IOException {
        //FileWriter fileWriter = new FileWriter(matchId + ".json");

    }

    private void getMatch() throws Exception {

        Integer[] apIds = {1026, 1052, 1056,
            1058, 3001, 3003,
            3020, 3023, 3025,
            3027, 3040, 3041,
            3050, 3057, 3060,
            3089, 3092, 3098,
            3100, 3108, 3113,
            3115, 3116, 3124,
            3135, 3136, 3145,
            3146, 3151, 3152,
            3157, 3165, 3174,
            3191, 3285, 3303,
            3504, 3708, 3716,
            3720, 3724};
        List<Integer> apIdsList = Arrays.asList(apIds);

        Files.walk(Paths.get("C:\\AP_ITEMS\\")).forEach(filePath -> {
            if (Files.isRegularFile(filePath)) {

                try (FileReader fileReader = new FileReader(filePath.toString())) {

                    File file = new File(filePath.toString());
                    String fileName = file.getName();
                    String region = fileName.replace(".json", "").toLowerCase();

                    JSONParser jsonParser = new JSONParser();
                    JSONArray matchId = (JSONArray) jsonParser.parse(fileReader);

                    for (int i = 0; i < jsonFileSize; i++) {

                        url = "https://" + region + ".api.pvp.net/api/lol/" + region + "/v2.2/match/" + matchId.get(i) + "?api_key=222fb254-63ca-4338-9b7d-397694c1e099";

                        System.out.println(url);
                        
                        File jsonFile = new File("C:\\Riot_API\\" + region + "\\" + matchId.get(i) + ".json");

                        if (!jsonFile.exists()) {
                            FileWriter fileWriter = new FileWriter(jsonFile);
                            BufferedWriter bw = new BufferedWriter(fileWriter);

                            URL obj = new URL(url);
                            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                            do {
                                // optional default is GET
                                con.setRequestMethod("GET");

                                int responseCode = con.getResponseCode();
                                System.out.println("\nSending 'GET' request to URL : " + url);
                                System.out.println("Response Code : " + responseCode);

                                if (responseCode == 500
                                        || responseCode == 503) {
                                    tryAgain = true;
                                } else {
                                    tryAgain = false;
                                }
                            } while (tryAgain);

                            StringBuffer response = new StringBuffer();

                            try (BufferedReader in
                                    = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                                String inputLine;

                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);
                                }

                                // do try
                                in.close();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            JSONObject jsonObject = new JSONObject(response.toString());

                            for (int n = 0; n < numberOfParticipants; n++) {

                                JSONObject stats = jsonObject.getJSONArray("participants").getJSONObject(n).getJSONObject("stats");
                                JSONObject object = new JSONObject();
                                JSONArray arr = new JSONArray();

                                    // String test = jsonObject.getJSONArray("participants").getJSONObject(i).getJSONObject("timeline").getString("lane");
                                for (int count = 0; count < itemSlots; count++) {
                                    if (stats.getInt("item" + count) != 0) {
                                        arr.add(stats.getInt("item" + count));
                                        itemList.add(stats.getInt("item" + count));
                                    }
                                }

                                object.put("participant" + (n + 1), arr);
                                bw.write(object.toString());
                            }
                            bw.flush();
                            bw.close();
                        }
                        /*
                         for (int itemId : itemList) {
                         if (apIdsList.contains(itemId)) {
                         fullApList.add(itemId);
                         }
                         }
                         */

                        // Sleep count came from request limit of 500 request per 10 minute.
                        // Therefore, 1.2 sec per request is allowed.
                        Thread.sleep(1200);
                    }

                    NumberFormat defaultFormat = NumberFormat.getPercentInstance();
                    defaultFormat.setMinimumFractionDigits(1);
/*
                    for (int value : apIdsList) {
                        double occurrences = Collections.frequency(fullApList, value);
                        System.out.println(defaultFormat.format(occurrences / itemList.size()));
                    }
*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // } catch (FileNotFoundException | IOException | ParseException e)        
    }

    private void createData() throws Exception {

    }

}
