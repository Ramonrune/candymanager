package com.candymanager.social;

import com.candymanager.social.publicar.PublicarView;

import java.util.List;

public class RedesSociaisDAO {

    private ConfiguracaoSharedPreferences configuracaoSharedPreferences;
    private List<PublicarView> publicarViews;

    public RedesSociaisDAO(){

    }

    public boolean postar(RedesSociaisModel redesSociaisModel){
        return true;
    }

    public List<PublicarView> listar(){
        return publicarViews;
    }
}
