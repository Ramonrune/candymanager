package com.candymanager.configuracao;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.candymanager.menu.MenuPrincipal;
import com.candymanager.util.Internet;
import com.candymanager.util.Mensagem;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.candymanager.R;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfiguracaoController extends Fragment implements AuthenticatorListener {

    private JanelaAutenticacaoInstagram janelaAutenticacaoInstagram;

    private View view;
    private CallbackManager callbackManager;
    private ConfiguracaoView configuracaoView;

    public ConfiguracaoController() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(false);

        ((MenuPrincipal) getActivity())
                .setActionBarTitle("Configurações");

        FacebookSdk.sdkInitialize(getContext());
        AppEventsLogger.activateApp(getContext());

        inicializaRecursos(inflater, container);


        callbackManager = CallbackManager.Factory.create();

/*
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email,user_posts");
        // If using in a fragment
        loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

               // LoginManager.getInstance().logInWithPublishPermissions(ConfiguracaoController.this, Arrays.asList("publish_actions"));
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });*/

        InstagramSharedPreferences instagramSharedPreferences = new InstagramSharedPreferences(getContext());
        if (instagramSharedPreferences.usuarioLogadoInstagram()) {
            configuracaoView.getInstagamSwitch().setChecked(true);
        }

        configuracaoView.getInstagamSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (Internet.possuiConexaoComRede(getActivity())) {
                    if (b) {
                        janelaAutenticacaoInstagram = new JanelaAutenticacaoInstagram(getContext(), ConfiguracaoController.this);
                        janelaAutenticacaoInstagram.setCancelable(true);
                        janelaAutenticacaoInstagram.show();
                    } else {
                        InstagramSharedPreferences instagramSharedPreferences = new InstagramSharedPreferences(getContext());
                        instagramSharedPreferences.setToken(null);
                    }

                } else {
                    Mensagem.mostrarDialogoFragment(getActivity(), "Internet", "Você precisa de conexão com a internet para se integrar ao Facebook!");
                    configuracaoView.getInstagamSwitch().setChecked(false);
                }

            }
        });


        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            configuracaoView.getFacebookSwitch().setChecked(true);
        }

        configuracaoView.getFacebookSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (Internet.possuiConexaoComRede(getActivity())) {
                    if (b) {
                        LoginManager.getInstance().logInWithReadPermissions(ConfiguracaoController.this, Arrays.asList("email,user_posts"));
                        configuracaoView.getFacebookSwitch().setChecked(true);

                    } else {
                        LoginManager.getInstance().logOut();
                        configuracaoView.getFacebookSwitch().setChecked(false);
                    }
                } else {
                    Mensagem.mostrarDialogoFragment(getActivity(), "Internet", "Você precisa de conexão com a internet para se integrar ao Facebook!");
                    configuracaoView.getFacebookSwitch().setChecked(false);
                }

            }
        });


        return view;
    }

    private void inicializaRecursos(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_configuracao, container, false);
        configuracaoView = new ConfiguracaoView(view);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCodeReceived(String auth_token) {

        InstagramSharedPreferences instagramSharedPreferences = new InstagramSharedPreferences(getContext());
        instagramSharedPreferences.setToken(auth_token);


    }
}
