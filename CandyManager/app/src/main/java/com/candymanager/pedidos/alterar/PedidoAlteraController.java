package com.candymanager.pedidos.alterar;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.candymanager.R;
import com.candymanager.menu.MenuPrincipal;
import com.candymanager.pedidos.PedidoController;
import com.candymanager.pedidos.PedidoDAO;
import com.candymanager.pedidos.PedidoModel;
import com.candymanager.produtos.ProdutoController;
import com.candymanager.produtos.ProdutoDAO;
import com.candymanager.produtos.ProdutoModel;
import com.candymanager.util.BitmapUtil;
import com.candymanager.util.Mensagem;
import com.candymanager.util.Validation;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.LinkedHashMap;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PedidoAlteraController extends Fragment {

    private PedidoAlteraView pedidoAlteraView;
    private PedidoModel model;
    private PedidoDAO pedidoDAO;

    private View view;
    private Validation validacao = new Validation();

    public PedidoAlteraController() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inicializaRecursos(inflater, container);

        inicializaListeners();
        ((MenuPrincipal) getActivity())
                .setActionBarTitle("Alterar pedido");

        pedidoAlteraView.getBairroEditText().setText(model.getBairro());
        pedidoAlteraView.getCepEditText().setText(model.getCep());
        pedidoAlteraView.getClienteEditText().setText(model.getCliente());
        pedidoAlteraView.getEnderecoEditText().setText(model.getEndereco());
        pedidoAlteraView.getNumeroEditText().setText(model.getNumero());

        return view;
    }

    private void inicializaRecursos(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_pedido_altera, container, false);;
        pedidoAlteraView = new PedidoAlteraView(view);
        pedidoDAO = new PedidoDAO(container.getContext());
        setHasOptionsMenu(true);
    }


    private void inicializaListeners() {
        pedidoAlteraView.getAgendaImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

             //   startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }


    public void setPedidoModel(PedidoModel model){
        this.model = model;

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);

        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.check).setVisible(true);

    }

    private Bitmap map = null;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.check:

                LinkedHashMap<EditText, String> mapa = new LinkedHashMap<>();
                mapa.put(pedidoAlteraView.getClienteEditText(), "Nome não pode ser vazio!");

                boolean success =  validacao.validaNaoNulo(mapa);

                if(success){
/*
                    model.setNome(produtoAlteraView.getNomeEditText().getText().toString());
                    model.setDescricao(produtoAlteraView.getDescricaoEditText().getText().toString());
*/
                    boolean ok = pedidoDAO.alterar(model);
                    PedidoController pedidoController = new PedidoController();

                    if(ok){
                        Mensagem.mostrarDialogoMudarFragmento(pedidoController, getActivity(), "Sucesso", "Produto alterado com sucesso!");
                    }
                    else{
                        Mensagem.mostrarDialogoMudarFragmento(pedidoController, getActivity(), "Erro", "Ocorreu um erro durante a alteração, por favor, tente novamente ou entre em contato com o suporte!");
                    }

                }

                return true;
            default:
                break;
        }

        return false;
    }
}
