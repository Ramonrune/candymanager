package com.candymanager.produtos.mostrar;




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
import com.candymanager.cliente.ClienteController;
import com.candymanager.cliente.ClienteDAO;
import com.candymanager.cliente.ClienteModel;
import com.candymanager.cliente.alterar.ClienteAlteraView;
import com.candymanager.cliente.mostrar.ClienteMostraView;
import com.candymanager.menu.MenuPrincipal;
import com.candymanager.produtos.ProdutoController;
import com.candymanager.produtos.ProdutoDAO;
import com.candymanager.produtos.ProdutoModel;
import com.candymanager.produtos.alterar.ProdutoAlteraView;
import com.candymanager.util.BitmapUtil;
import com.candymanager.util.Mensagem;
import com.candymanager.util.Validation;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.LinkedHashMap;

import static android.app.Activity.RESULT_OK;


public class ProdutoMostraController extends Fragment {


    private ProdutoMostraView produtoMostraView;
    private ProdutoModel model;
    private ProdutoDAO produtoDAO;

    private View view;

    public ProdutoMostraController() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inicializaRecursos(inflater, container);

        ((MenuPrincipal) getActivity())
                .setActionBarTitle(model.getNome());

        produtoMostraView.getNomeEditText().setText(model.getNome());
        produtoMostraView.getDescricaoEditText().setText(model.getDescricao());
        produtoMostraView.getFotoImageButton().setImageBitmap(BitmapUtil.getImage(model.getImagem()));

        return view;
    }

    private void inicializaRecursos(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_produto_mostra, container, false);;
        produtoMostraView = new ProdutoMostraView(view);
        produtoDAO = new ProdutoDAO(container.getContext());
        setHasOptionsMenu(true);


    }



    public void setProdutoModel(ProdutoModel model){
        this.model = model;

    }



}
