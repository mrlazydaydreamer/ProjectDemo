package com.example.hrish.projectdemo;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginProcess extends AsyncTask<String,String,String> {

    public interface LoginResult{
        void loginSuccessful();
        void loginFailed();
    }

    private LoginResult result ;
    LoginProcess(LoginResult result){
        this.result = result;
    }



    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL("www");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent","APP_USER");


            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(("email="+strings[0]).getBytes());
            os.write(("password"+strings[1]).getBytes());
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                System.out.println(response.toString());
            } else {
                System.out.println("Login Failed Error:L010");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s.equals("Successful")){
            result.loginSuccessful();
        }
        else {
            result.loginFailed();
        }
    }
}
