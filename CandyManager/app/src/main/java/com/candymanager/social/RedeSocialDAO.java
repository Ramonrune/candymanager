package com.candymanager.social;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.candymanager.R;
import com.candymanager.configuracao.InstagramSharedPreferences;
import com.candymanager.configuracao.TwitterApp;
import com.candymanager.configuracao.TwitterSessao;
import com.candymanager.social.adapter.RedeSocialAdapter;
import com.candymanager.social.publicar.RedeSocialPublicaView;
import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import bolts.Task;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class RedeSocialDAO {

    private ArrayList<RedeSocialModel> list = new ArrayList<>();
    private Context context;
    private RedeSocialListaPublicacaoView redeSocialListaPublicacaoView;
    private InstagramSharedPreferences instagramSharedPreferences;
    private static String URL_FACEBOOK;
    private static String URL_INSTAGRAM;
    private AsyncTaskSharedPreferences asyncTaskSharedPreferences;
    private TwitterApp twitterApp;
    private TwitterSessao twitterSessao;

    public RedeSocialDAO(Context context, RedeSocialListaPublicacaoView redeSocialListaPublicacaoView) {
        this.context = context;
        this.redeSocialListaPublicacaoView = redeSocialListaPublicacaoView;
        this.instagramSharedPreferences = new InstagramSharedPreferences(context);
        this.asyncTaskSharedPreferences = new AsyncTaskSharedPreferences(context, getQuantidadeRedesSociaisIntegradas());
        this.twitterApp = new TwitterApp(context, "bMeIFl9LZ17tgJkSDqIrEL3xD", "URisH2K8zt6FfZF6DHD31zejZiWrAgqoy45phuydmTL1rQ6fKp");
        this.twitterSessao = new TwitterSessao(context);
        if (AccessToken.getCurrentAccessToken() != null) {
            URL_FACEBOOK = "https://graph.facebook.com/v3.0/me/feed?fields=comments.limit(0).summary(true),likes.limit(0).summary(true),description,picture,full_picture,created_time&access_token=" + AccessToken.getCurrentAccessToken().getToken();
        }

        if (instagramSharedPreferences.usuarioLogadoInstagram()) {
            URL_INSTAGRAM = "https://api.instagram.com/v1/users/self/media/recent/?access_token=" + instagramSharedPreferences.getToken();
        }

    }

    public void start() {
        redeSocialListaPublicacaoView.getProgressoProgressBar().setVisibility(View.VISIBLE);
        redeSocialListaPublicacaoView.getListaPublicacoesListView().setVisibility(View.INVISIBLE);

        if(isTwitterIntegrated()){
            new TwitterDAO().execute("");
        }

        if (isFacebookIntegrated()) {
            new FacebookDAO().execute("");
        }

        if (isInstagramIntegrated()) {
            new InstagramDAO().execute("");
        }



    }

    private boolean isTwitterIntegrated() {
        if (twitterApp.hasAccessToken()) {
            return true;
        }

        return false;
    }

    private boolean isFacebookIntegrated() {
        if (AccessToken.getCurrentAccessToken() != null) {
            return true;
        }

        return false;
    }

    private boolean isInstagramIntegrated() {
        if (instagramSharedPreferences.usuarioLogadoInstagram()) {
            return true;
        }

        return false;
    }


    public boolean isIntegrated() {
        if (AccessToken.getCurrentAccessToken() != null) {
            return true;
        }

        if (instagramSharedPreferences.usuarioLogadoInstagram()) {
            return true;
        }

        return false;
    }

    public int getQuantidadeRedesSociaisIntegradas() {
        int contador = 0;
        if (AccessToken.getCurrentAccessToken() != null) {
            contador++;
        }

        if (instagramSharedPreferences.usuarioLogadoInstagram()) {
            contador++;
        }
        return contador;
    }

    private class TwitterDAO{

        public void execute(String task){

            ResponseList<Status> listTwitter = null;
            try {
                listTwitter = twitterApp.getmTwitter().getUserTimeline(twitterSessao.getUsername());
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            for (Status each : listTwitter) {


                RedeSocialModel redeSocialModel = new RedeSocialModel();
                redeSocialModel.setType(3);

                if(each.getMediaEntities().length > 0){


                    redeSocialModel.setPicture(each.getMediaEntities()[0].getMediaURLHttps());
                    redeSocialModel.setFullPicture(each.getMediaEntities()[0].getMediaURLHttps());
                }
                else{
                    redeSocialModel.setPicture("https://pbs.twimg.com/profile_images/875135141135302656/eiM2Wz66_400x400.jpg");
                    redeSocialModel.setFullPicture("https://pbs.twimg.com/profile_images/875135141135302656/eiM2Wz66_400x400.jpg");
                }

                redeSocialModel.setLikes(each.getFavoriteCount());
                redeSocialModel.setComments(each.getRetweetCount());
                redeSocialModel.setDescription(each.getText());


                System.out.println(each.getCreatedAt().toString());
                redeSocialModel.setCreatedTime(each.getCreatedAt().toString());
                list.add(redeSocialModel);

                System.out.println("Sent by: @" + each.getUser().getScreenName()
                        + " - " + each.getUser().getName() + "\n" + each.getText()
                        + "\n");
            }

        }
    }


    private class FacebookDAO extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String jsonStr) {


            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray jsonRoot = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonRoot.length(); i++) {


                    RedeSocialModel redeSocialModel = new RedeSocialModel();
                    redeSocialModel.setType(1);
                    JSONObject jsonItem = jsonRoot.getJSONObject(i);

                    redeSocialModel.setPicture(jsonItem.isNull("picture") ? "" : jsonItem.getString("picture"));
                    redeSocialModel.setFullPicture(jsonItem.isNull("full_picture") ? "" : jsonItem.getString("full_picture"));
                    redeSocialModel.setId(jsonItem.isNull("id") ? "" : jsonItem.getString("id"));
                    redeSocialModel.setCreatedTime(jsonItem.isNull("created_time") ? "" : jsonItem.getString("created_time"));
                    redeSocialModel.setDescription(jsonItem.isNull("description") ? "" : jsonItem.getString("description"));
                    JSONObject jsonObject1 = jsonItem.getJSONObject("likes");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("summary");

                    redeSocialModel.setLikes(jsonObject2.isNull("total_count") ? -1 : jsonObject2.getInt("total_count"));

                    jsonObject1 = jsonItem.getJSONObject("comments");
                    jsonObject2 = jsonObject1.getJSONObject("summary");

                    redeSocialModel.setComments(jsonObject2.isNull("total_count") ? -1 : jsonObject2.getInt("total_count"));


                    list.add(redeSocialModel);


                }
                asyncTaskSharedPreferences.incrementaCounter();


                if(asyncTaskSharedPreferences.terminou()){

                    Collections.sort(list, new Comparator<RedeSocialModel>() {
                        @Override
                        public int compare(RedeSocialModel redeSocialModel, RedeSocialModel redeSocialModel2) {
                            DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            try {
                                return f.parse(redeSocialModel2.getCreatedTime()).compareTo(f.parse(redeSocialModel.getCreatedTime()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            return 0;

                        }
                    });

                    redeSocialListaPublicacaoView.getListaPublicacoesListView().setAdapter(new RedeSocialAdapter(context, list));
                    redeSocialListaPublicacaoView.getProgressoProgressBar().setVisibility(View.INVISIBLE);
                    redeSocialListaPublicacaoView.getListaPublicacoesListView().setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... dados) {

            return requestDados(URL_FACEBOOK);
        }
    }

    private class InstagramDAO extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String jsonStr) {

            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray jsonRoot = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonRoot.length(); i++) {


                    RedeSocialModel redeSocialModel = new RedeSocialModel();
                    redeSocialModel.setType(2);
                    JSONObject jsonItem = jsonRoot.getJSONObject(i);

                    redeSocialModel.setId(jsonItem.isNull("id") ? "" : jsonItem.getString("id"));
                    redeSocialModel.setCreatedTime(jsonItem.isNull("created_time") ? "" : jsonItem.getString("created_time"));
                    redeSocialModel.setDescription(jsonItem.isNull("caption") ? "" : jsonItem.getString("caption"));


                    JSONObject jsonObject1 = jsonItem.getJSONObject("images");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("thumbnail");

                    redeSocialModel.setPicture(jsonObject2.isNull("url") ? "" : jsonObject2.getString("url"));


                    jsonObject1 = jsonItem.getJSONObject("images");
                    jsonObject2 = jsonObject1.getJSONObject("standard_resolution");

                    redeSocialModel.setFullPicture(jsonObject2.isNull("url") ? "" : jsonObject2.getString("url"));

                    jsonObject1 = jsonItem.getJSONObject("likes");
                    redeSocialModel.setLikes(jsonObject1.isNull("count") ? -1 : jsonObject1.getInt("count"));

                    jsonObject1 = jsonItem.getJSONObject("comments");
                    redeSocialModel.setComments(jsonObject1.isNull("count") ? -1 : jsonObject1.getInt("count"));


                    list.add(redeSocialModel);


                }


                asyncTaskSharedPreferences.incrementaCounter();

                if(asyncTaskSharedPreferences.terminou()){

                    Collections.sort(list, new Comparator<RedeSocialModel>() {
                        @Override
                        public int compare(RedeSocialModel redeSocialModel, RedeSocialModel redeSocialModel2) {
                            DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            try {
                                return f.parse(redeSocialModel2.getCreatedTime()).compareTo(f.parse(redeSocialModel.getCreatedTime()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            return 0;

                        }
                    });

                    redeSocialListaPublicacaoView.getListaPublicacoesListView().setAdapter(new RedeSocialAdapter(context, list));
                    redeSocialListaPublicacaoView.getProgressoProgressBar().setVisibility(View.INVISIBLE);
                    redeSocialListaPublicacaoView.getListaPublicacoesListView().setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... dados) {
            return requestDados(URL_INSTAGRAM);
        }
    }


    private String requestDados(String URL_API) {

        System.out.println(URL_API);
        Uri builtUri = Uri.parse(URL_API).buildUpon()
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
            System.out.println("1");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {

                return null;
            }

            System.out.println("2");

            String jsonStr = buffer.toString();
            System.out.println("3 - " + jsonStr);


            return jsonStr;
        } catch (Exception e) {
            System.out.println("errroooo - " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<RedeSocialModel> getList() {
        return list;
    }


}
