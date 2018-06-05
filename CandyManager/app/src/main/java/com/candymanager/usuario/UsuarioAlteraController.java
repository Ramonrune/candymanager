package com.candymanager.usuario;

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
import com.candymanager.usuario.UsuarioAlteraDAO;
import com.candymanager.usuario.UsuarioModel;
import com.candymanager.usuario.UsuarioAlteraView;
import com.candymanager.menu.MenuPrincipal;
import com.candymanager.util.BitmapUtil;
import com.candymanager.util.Mensagem;
import com.candymanager.util.Validation;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.LinkedHashMap;

import static android.app.Activity.RESULT_OK;

public class UsuarioAlteraController extends Fragment {


    private UsuarioAlteraView usuarioAlteraView;
    private UsuarioModel model;
    private UsuarioAlteraDAO usuarioDAO;

    private View view;
    private Validation validacao = new Validation();
    private static int RESULT_LOAD_IMAGE = 1;

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

        usuarioAlteraView.getEmailEditText().setText(model.getEmail());
        usuarioAlteraView.getNomeEditText().setText(model.getNome());
        usuarioAlteraView.getCepEditText().setText(model.getCep());
        usuarioAlteraView.getEnderecoEditText().setText(model.getEndereco());
        usuarioAlteraView.getBairroEditText().setText(model.getBairro());
        usuarioAlteraView.getNumeroEditText().setText(model.getNumero());
        usuarioAlteraView.getFotoImageButton().setImageBitmap(BitmapUtil.getImage(model.getFoto()));
        map = BitmapUtil.getImage(model.getFoto());

        return view;
    }

    private void inicializaRecursos(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_altera_usuario, container, false);;
        usuarioAlteraView = new UsuarioAlteraView(view);
        usuarioDAO = new UsuarioAlteraDAO(container.getContext());
        setHasOptionsMenu(true);


    }

    private void inicializaListeners() {
        usuarioAlteraView.getFotoImageButton().setOnClickListener(new View.OnClickListener() {
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
            usuarioAlteraView.getFotoImageButton().setImageBitmap(bmp);

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
    




}
