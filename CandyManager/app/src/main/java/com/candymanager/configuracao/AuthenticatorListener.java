package com.candymanager.configuracao;

/**
 * Created by Usuario on 20/05/2018.
 */


public interface AuthenticatorListener {
    void onCodeReceived(String auth_token);

}
