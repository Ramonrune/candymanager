package com.candymanager.configuracao;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.candymanager.R;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class JanelaAutenticacaoInstagram extends Dialog {

    private final AuthenticatorListener listener;
    private Context context;

    private WebView web_view;

    private final String url = "https://api.instagram.com/"
            + "oauth/authorize/?client_id="
            + "97310af13f5843e789c6c7df9f1432e0"
            + "&redirect_uri="
            + "http://www.fatec.edu.br/"
            + "&response_type=token"
            + "&display=touch&scope=public_content";

    public JanelaAutenticacaoInstagram(@NonNull Context context, AuthenticatorListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialogo_autenticacao);
        initializeWebView();
    }

    private void initializeWebView() {
        web_view = (WebView) findViewById(R.id.web_view);
        web_view.loadUrl("https://www.instagram.com/accounts/login/?next=/oauth/authorize/%3Fclient_id%3D97310af13f5843e789c6c7df9f1432e0%26redirect_uri%3Dhttp%3A//www.fatec.edu.br/%26response_type%3Dtoken%26display%3Dtouch%26scope%3Dpublic_content");
        web_view.getSettings().setAllowContentAccess(false);

        System.out.println(url + " ---------");
        web_view.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }

            boolean authComplete = false;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                System.out.println("pagina iniciandoooo");
            }

            String access_token;

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                System.out.println("testeeeee");

                System.out.println(url);

                if (url.contains("#access_token=") && !authComplete) {
                    Uri uri = Uri.parse(url);
                    access_token = uri.getEncodedFragment();
                    // get the whole token after the '=' sign
                    access_token = access_token.substring(access_token.lastIndexOf("=") + 1);
                    Log.i("", "CODE : " + access_token);
                    authComplete = true;
                    listener.onCodeReceived(access_token);
                    dismiss();

                } else if (url.contains("?error")) {
                    Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {

                    System.out.println("rodouuuuu");
                    web_view.setWebViewClient(v);
                    web_view.loadUrl(url);
                }
            }
        });

    }

    WebViewClient v =  new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return true;
        }

        boolean authComplete = false;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        String access_token;

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);



            if (url.contains("#access_token=") && !authComplete) {
                Uri uri = Uri.parse(url);
                access_token = uri.getEncodedFragment();
                // get the whole token after the '=' sign
                access_token = access_token.substring(access_token.lastIndexOf("=") + 1);
                Log.i("", "CODE : " + access_token);
                authComplete = true;
                listener.onCodeReceived(access_token);
                dismiss();

            } else if (url.contains("?error")) {
                Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        }
    };

}