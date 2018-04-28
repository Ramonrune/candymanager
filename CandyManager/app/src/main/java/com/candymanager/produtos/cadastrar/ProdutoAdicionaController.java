package com.candymanager.produtos.cadastrar;



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


public class ProdutoAdicionaController extends Fragment {


    private ProdutoAdicionaView produtoAdicionaView;
    private ProdutoModel model = new ProdutoModel();
    private ProdutoDAO produtoDAO;

    private View view;
    private Validation validacao = new Validation();
    private static int RESULT_LOAD_IMAGE = 1;

    public ProdutoAdicionaController() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inicializaRecursos(inflater, container);

        inicializaListeners();
        ((MenuPrincipal) getActivity())
                .setActionBarTitle("Adicionar produto");


        return view;
    }

    private void inicializaRecursos(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_produto_adiciona, container, false);;
        produtoAdicionaView = new ProdutoAdicionaView(view);
        produtoDAO = new ProdutoDAO(container.getContext());
        setHasOptionsMenu(true);


    }

    private void inicializaListeners() {
        produtoAdicionaView.getFotoImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();



            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
                bmp = Bitmap.createScaledBitmap(bmp, 300, 300, true);
                bmp = BitmapUtil.getCroppedBitmap(bmp);
                map = bmp;
            } catch (IOException e) {

            }
            produtoAdicionaView.getFotoImageButton().setImageBitmap(bmp);

        }


    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
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
                mapa.put(produtoAdicionaView.getNomeEditText(), "Nome não pode ser vazio!");


                boolean success =  validacao.validaNaoNulo(mapa);

                if(success){

                    model.setNome(produtoAdicionaView.getNomeEditText().getText().toString());
                    model.setDescricao(produtoAdicionaView.getDescricaoEditText().getText().toString());

                    if(map == null){
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.produto);
                        bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                        bitmap = BitmapUtil.getCroppedBitmap(bitmap);

                        model.setImagem(BitmapUtil.getBytes(bitmap));
                    }
                    else{
                        model.setImagem(BitmapUtil.getBytes(map));
                    }


                    boolean ok = produtoDAO.cadastrar(model);
                    ProdutoController produtoController = new ProdutoController();

                    if(ok){
                        Mensagem.mostrarDialogoMudarFragmento(produtoController, getActivity(), "Sucesso", "Produto cadastrado com sucesso!");
                    }
                    else{
                        Mensagem.mostrarDialogoMudarFragmento(produtoController, getActivity(), "Erro", "Ocorreu um erro durante o cadastro, por favor, tente novamente ou entre em contato com o suporte!");
                    }

                }

                return true;
            default:
                break;
        }

        return false;
    }



}
