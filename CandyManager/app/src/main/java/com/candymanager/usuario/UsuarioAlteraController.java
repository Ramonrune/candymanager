package com.candymanager.usuario;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;

import com.candymanager.R;
import com.candymanager.db.UsuarioAjudante;
import com.candymanager.db.UsuarioContrato;
import com.candymanager.home.HomeController;
import com.candymanager.login.LoginSharedPreferences;
import com.candymanager.pedidos.ItemPedidoModel;
import com.candymanager.pedidos.PedidoController;
import com.candymanager.pedidos.alterar.ItemPedidoAlteraView;
import com.candymanager.usuario.UsuarioAlteraDAO;
import com.candymanager.usuario.UsuarioModel;
import com.candymanager.usuario.UsuarioAlteraView;
import com.candymanager.menu.MenuPrincipal;
import com.candymanager.util.BitmapUtil;
import com.candymanager.util.Mensagem;
import com.candymanager.util.UUIDGenerator;
import com.candymanager.util.Validation;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.LinkedHashMap;

import static android.app.Activity.RESULT_OK;

public class UsuarioAlteraController extends Fragment {


    private UsuarioAlteraView usuarioAlteraView;
    private UsuarioAjudante usuarioAjudante;
    private UsuarioModel model;
    private UsuarioAlteraDAO usuarioDAO;

    private View view;
    private Validation validacao = new Validation();
    private static int RESULT_LOAD_IMAGE = 1;
    private Intent i;

    public UsuarioAlteraController() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inicializaRecursos(inflater, container);

        inicializaListeners();
        ((MenuPrincipal) getActivity())
                .setActionBarTitle("Alterar usuário");

        iniciaCampos(this.getContext());
        return view;
    }

    private void inicializaRecursos(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_altera_usuario, container, false);
        usuarioAlteraView = new UsuarioAlteraView(view);
        usuarioDAO = new UsuarioAlteraDAO(container.getContext());
        model = new UsuarioModel();
        setHasOptionsMenu(true);

    }

    private void inicializaListeners() {
        usuarioAlteraView.getFotoImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    private void iniciaCampos(Context context){

        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(context);

        UsuarioAlteraDAO usuarioDAO = new UsuarioAlteraDAO(context);

        model = usuarioDAO.getUsuarioModel();

        usuarioAlteraView.getEmailEditText().setText(model.getEmail());
        usuarioAlteraView.getNomeEditText().setText(model.getNome());
        usuarioAlteraView.getCepEditText().setText(model.getCep());
        usuarioAlteraView.getEnderecoEditText().setText(model.getEndereco());
        usuarioAlteraView.getBairroEditText().setText(model.getBairro());
        usuarioAlteraView.getNumeroEditText().setText(model.getNumero());

        if(model.getFoto() !=  null){
            usuarioAlteraView.getFotoImageButton().setImageBitmap(BitmapUtil.getImage(model.getFoto()));
        }
        else{
            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.usuario);
            usuarioAlteraView.getFotoImageButton().setImageBitmap(Bitmap.createScaledBitmap( icon, 300, 300, true));
        }

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
                fotoUsuario = bmp;
            } catch (IOException e) {

            }
            usuarioAlteraView.getFotoImageButton().setImageBitmap(fotoUsuario);
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

    public void setUsuarioModel(UsuarioModel model){
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


    private Bitmap fotoUsuario = null;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.check:

                boolean sucesso = true;

                if (model.getNome() == null) {
                    Mensagem.mostrarDialogoFragment(getActivity(), "Nome", "Nome não pode estar vazio!");
                    sucesso = false;
                }

                if (sucesso) {
                    model.setNome(usuarioAlteraView.getNomeEditText().getText().toString());
                    if(fotoUsuario != null){
                        model.setFoto(BitmapUtil.getBytes(fotoUsuario));
                    }
                    model.setBairro(usuarioAlteraView.getBairroEditText().getText().toString());
                    model.setEndereco(usuarioAlteraView.getEnderecoEditText().getText().toString());
                    model.setNumero(usuarioAlteraView.getNumeroEditText().getText().toString());
                    model.setCep(usuarioAlteraView.getCepEditText().getText().toString().replace("-", ""));
                }

                boolean alterar = false;

                usuarioDAO.alterar(model);

                alterar = usuarioDAO.alterar(model);

                HomeController homeController = new HomeController();

                if (sucesso) {

                    if (alterar) {
                        if(fotoUsuario != null) {
                            ((MenuPrincipal) getActivity())
                                    .setFoto(fotoUsuario);
                        }
                        Mensagem.mostrarDialogoMudarFragmento(homeController, getActivity(), "Sucesso", "Alteração do usuário realizado com sucesso!");
                    } else {
                        Mensagem.mostrarDialogoMudarFragmento(homeController, getActivity(), "Erro", "Ocorreu algum erro alteração, por favor, tente novamente ou entre em contato com o suporte!");
                    }
                }
                return true;

            default:
                break;
        }

        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(getContext());

        loginSharedPreferences.setUsuarioLogado(loginSharedPreferences.getId(),model.getNome(),model.getEmail());

        return false;
    }

}
