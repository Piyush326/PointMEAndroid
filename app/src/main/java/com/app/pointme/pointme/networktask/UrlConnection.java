package com.app.pointme.pointme.networktask;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class UrlConnection {

    private final String BASE_URL = "";//put your base URL here.

    //    private String getResponse(String url, HashMap<String, String> params) {
    private String getResponse(String url) {
        try {
            URL url1 = new URL(url);//put your url here for hitting an particular service .
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setReadTimeout(10000);/* milliseconds */
            conn.setConnectTimeout(15000);/* milliseconds *///can increase this time to make connection time out response.
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            int response = conn.getResponseCode();
            Log.d("response", "The response is: " + response);

            InputStream is = conn.getInputStream();
            String contentAsString = readStream(is);
            return contentAsString;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (ProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Reads the stream into a string
     *
     * @param iStream the input stream
     * @return the string read from the stream
     * @throws IOException when an IO error occurs
     */

    private String readStream(InputStream iStream) throws IOException {
        //build a Stream Reader, it can read char by char
        InputStreamReader iStreamReader = new InputStreamReader(iStream);
        //build a buffered Reader, so that i can read whole line at once
        BufferedReader bReader = new BufferedReader(iStreamReader);
        String line = null;
        StringBuilder builder = new StringBuilder();
        while ((line = bReader.readLine()) != null) {  //Read till end
            builder.append(line);
        }
        bReader.close();         //close all opened stuff
        iStreamReader.close();
        //iStream.close(); //EDIT: Let the creator of the stream close it!
        // some readers may auto close the inner stream
        return builder.toString();
    }

/*
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String readStream(InputStream iStream) throws IOException {

        //Buffered reader allows us to read line by line
        try (BufferedReader bReader =
                     new BufferedReader(new InputStreamReader(iStream))){
            StringBuilder builder = new StringBuilder();
            String line;
            while((line = bReader.readLine()) != null) {  //Read till end
                builder.append(line);
            }
            return builder.toString();
        }
    }
*/


    private String readIt(InputStream stream, int len) throws IOException {
        Reader reader;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    public String getDeals() {
        String response = getResponse("http://api.androidhive.info/contacts/");
        return response;
    }


}
