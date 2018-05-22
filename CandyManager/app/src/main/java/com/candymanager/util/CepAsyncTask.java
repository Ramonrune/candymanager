package com.candymanager.util;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Usuario on 19/05/2018.
 */

public class CepAsyncTask extends AsyncTask<String, Void, HashMap<String, String>> {


    @Override
    protected HashMap<String, String> doInBackground(String... cep) {
        return requestCep(cep[0]);
    }

    private HashMap<String, String> requestCep(String cep){
        Uri builtUri = Uri.parse("https://viacep.com.br/ws/" + cep.toString().replaceAll("[^\\d.]", "")+ "/json/").buildUpon()
                .build();

        try {
            URL url = new URL(builtUri.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {

                return null;
            }
            String jsonStr = buffer.toString();


            return formataJson(jsonStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private HashMap<String, String> formataJson(String jsonStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonStr);

        String logradouro = jsonObject.getString("logradouro");
        String bairro = jsonObject.getString("bairro");
        String localidade = jsonObject.getString("localidade");
        String uf = jsonObject.getString("uf");



        HashMap<String, String> map = new HashMap<>();
        map.put("logradouro", logradouro + " - " + localidade + " - " + uf);
        map.put("bairro", bairro);

        return map;
    }


}
