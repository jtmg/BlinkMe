package com.example.topog.blinkme;

/**
 * Created by topog on 06/01/2018.
 */


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class Send extends AsyncTask<String,String,String>
{
    @Override
    protected String doInBackground(String... params) {
        String request = params[0];
        StringBuffer result = new StringBuffer("");
        try{
            URL url = new URL(request);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = rd.readLine()) != null) result.append(line);

        }catch (SocketTimeoutException e) {
            return "FailedConnection";
        } catch(ConnectException e) {
            return "FailedConnection";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}



