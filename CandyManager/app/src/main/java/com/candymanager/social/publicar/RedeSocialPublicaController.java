package com.candymanager.social.publicar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.candymanager.R;
import com.candymanager.menu.MenuPrincipal;
import com.candymanager.util.BitmapUtil;
import com.facebook.AccessToken;

import java.io.IOException;


import java.io.FileDescriptor;

import static android.app.Activity.RESULT_OK;

@SuppressLint("ValidFragment")
public class RedeSocialPublicaController extends android.support.v4.app.Fragment {


    private View view;
    private RedeSocialPublicaView redeSocialPublicaView;

    public RedeSocialPublicaController() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        ((MenuPrincipal) getActivity())
                .setActionBarTitle("Publicar");

        inicializaRecursos(inflater, container);
        inicializaListeners();


        return view;
    }

    private void inicializaListeners() {
        redeSocialPublicaView.getImageDoceImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    private void inicializaRecursos(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_rede_social_publicar, container, false);
        redeSocialPublicaView = new RedeSocialPublicaView(view);


    }

    private static int RESULT_LOAD_IMAGE = 1;

    private Uri uri;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            uri = selectedImage;
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();


            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
                bmp = Bitmap.createScaledBitmap(bmp, 600, 600, true);
                bmp = BitmapUtil.getCroppedBitmap(bmp);
                map = bmp;


            } catch (IOException e) {

            }
            redeSocialPublicaView.getImageDoceImageButton().setImageBitmap(bmp);

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
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM,uri);
                i.putExtra(Intent.EXTRA_TEXT, redeSocialPublicaView.getLegendaEditText().getText().toString());

                //  startActivity(i);

                startActivity(Intent.createChooser(i, "Share Image"));
               // ShareDialog.show(RedeSocialPublicaController.this, sharePhotoContent);
                return true;
            default:
                break;
        }

        return false;
    }









}
