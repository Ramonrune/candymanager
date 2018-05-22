package com.candymanager.social;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.candymanager.R;
import com.candymanager.menu.MenuPrincipal;
import com.candymanager.social.publicar.RedeSocialPublicaController;
import com.candymanager.util.Internet;
import com.candymanager.util.Mensagem;

public class RedeSocialController extends android.support.v4.app.Fragment {

    private RedeSocialDAO redeSocialDAO;
    private RedeSocialModel redesSociaisModel;
    private RedeSocialListaPublicacaoView redeSocialListaPublicacaoView;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*LoginManager.getInstance().logInWithReadPermissions(
                RedeSocialController.this,
                Arrays.asList("user_posts"));*/
        setHasOptionsMenu(false);

        ((MenuPrincipal) getActivity())
                .setActionBarTitle("Redes sociais");

        inicializaRecursos(inflater, container);
        inicializaListeners();


        return view;
    }

    private void inicializaListeners() {
        redeSocialListaPublicacaoView.getListaPublicacoesListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {


            }
        });

        redeSocialListaPublicacaoView.getAdicionaPublicacaoFloatingActionButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RedeSocialPublicaController redeSocialPublicaController = new RedeSocialPublicaController();
                android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();

                manager.beginTransaction().replace(R.id.relative_layout_fragmento, redeSocialPublicaController, redeSocialPublicaController.getTag()).addToBackStack(null).commit();

            }
        });
    }

    private void inicializaRecursos(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_rede_social, container, false);
        redeSocialListaPublicacaoView = new RedeSocialListaPublicacaoView(view);
        redeSocialDAO = new RedeSocialDAO(getContext(), redeSocialListaPublicacaoView);

        if (Internet.possuiConexaoComRede(getActivity())) {
            if (redeSocialDAO.isIntegrated()) {
                redeSocialDAO.start();

            } else {
                view = inflater.inflate(R.layout.fragment_rede_social_sem_integracao, container, false);

            }
        }
        else{
            redeSocialListaPublicacaoView.getProgressoProgressBar().setVisibility(View.INVISIBLE);
            Mensagem.mostrarDialogoFragment(getActivity(), "Internet", "Você precisa de conexão com a internet para visualizar as postagens nas redes sociais!");

        }


        redesSociaisModel = new RedeSocialModel();
    }


}
