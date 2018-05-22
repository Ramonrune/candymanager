package com.candymanager.social;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.candymanager.R;
import com.candymanager.social.publicar.RedeSocialPublicaController;
import com.candymanager.social.publicar.RedeSocialPublicaView;
import com.futuremind.recyclerviewfastscroll.FastScroller;

public class RedeSocialListaPublicacaoView{

    private ListView listaPublicacoesListView;
    private FloatingActionButton adicionaPublicacaoFloatingActionButton;
    private ProgressBar progressoProgressBar;

    public RedeSocialListaPublicacaoView(View view) {

        listaPublicacoesListView = (ListView) view.findViewById(R.id.listaRedesSociaisListView);
        adicionaPublicacaoFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.novaPublicacaoFloatingActionButton);
        progressoProgressBar = (ProgressBar) view.findViewById(R.id.progressoProgressBar);
        int color = Color.parseColor("#7E7EBE");
        progressoProgressBar.getIndeterminateDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY);

        progressoProgressBar.setVisibility(View.VISIBLE);
        listaPublicacoesListView.setVisibility(View.INVISIBLE);

    }

    public ListView getListaPublicacoesListView() {
        return listaPublicacoesListView;
    }

    public FloatingActionButton getAdicionaPublicacaoFloatingActionButton() {
        return adicionaPublicacaoFloatingActionButton;
    }

    public ProgressBar getProgressoProgressBar() {
        return progressoProgressBar;
    }
}
