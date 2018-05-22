package com.candymanager.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.candymanager.R;
import com.candymanager.SplashScreen;
import com.candymanager.cadastro.CadastroController;
import com.candymanager.cliente.ClienteController;
import com.candymanager.login.LoginController;
import com.candymanager.menu.MenuPrincipal;

/**
 * Created by Usuario on 19/04/2018.
 */

public class Mensagem {

    public static void mostrarDialogo(AppCompatActivity activity, String titulo, String mensagem){
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(mensagem);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void mostrarDialogoFragment(Activity activity, String titulo, String mensagem){
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(mensagem);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void mostrarDialogoMudarFragmento(final android.support.v4.app.Fragment fragment, final FragmentActivity fragmentActivity, String titulo, String mensagem){
        AlertDialog alertDialog = new AlertDialog.Builder(fragmentActivity).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(mensagem);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.relative_layout_fragmento, fragment, fragment.getTag()).commit();
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void mostrarErro(AppCompatActivity activity){
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle("Erro");
        alertDialog.setMessage("Um problema ocorreu, por favor, entre em contato com o suporte ou tente novamente mais tarde!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    public static void mostrarDialogoMudarActivity(final AppCompatActivity activity, final Class destino, String titulo, String mensagem){
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(mensagem);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(activity,
                                destino);

                        activity.startActivity(intent);

                        activity.finish();
                        dialog.dismiss();

                    }
                });
        alertDialog.show();
    }
}
