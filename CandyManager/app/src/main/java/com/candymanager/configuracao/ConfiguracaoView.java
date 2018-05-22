package com.candymanager.configuracao;

import android.view.View;
import android.widget.Switch;

import com.candymanager.R;

/**
 * Created by Usuario on 20/05/2018.
 */

public class ConfiguracaoView {

    private Switch facebookSwitch;
    private Switch instagamSwitch;
    private Switch twitterSwitch;


    public ConfiguracaoView(View view) {
        instagamSwitch  = (Switch) view.findViewById(R.id.instagramSwitch);
        facebookSwitch  = (Switch) view.findViewById(R.id.facebookSwitch);
    }

    public Switch getFacebookSwitch() {
        return facebookSwitch;
    }

    public void setFacebookSwitch(Switch facebookSwitch) {
        this.facebookSwitch = facebookSwitch;
    }

    public Switch getInstagamSwitch() {
        return instagamSwitch;
    }

    public void setInstagamSwitch(Switch instagamSwitch) {
        this.instagamSwitch = instagamSwitch;
    }

    public Switch getTwitterSwitch() {
        return twitterSwitch;
    }

    public void setTwitterSwitch(Switch twitterSwitch) {
        this.twitterSwitch = twitterSwitch;
    }
}
