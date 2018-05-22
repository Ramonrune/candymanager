package com.candymanager.menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.candymanager.R;
import com.candymanager.SplashScreen;
import com.candymanager.cliente.ClienteController;
import com.candymanager.configuracao.ConfiguracaoController;
import com.candymanager.db.ProdutoContrato;
import com.candymanager.home.HomeController;
import com.candymanager.login.LoginController;
import com.candymanager.login.LoginSharedPreferences;
import com.candymanager.pedidos.PedidoController;
import com.candymanager.produtos.ProdutoController;
import com.candymanager.social.RedeSocialController;

public class MenuPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.setCheckedItem(R.id.nav_home);

        HomeController homeController = new HomeController();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.relative_layout_fragmento, homeController, homeController.getTag()).commit();

        View header=navigationView.getHeaderView(0);

        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(getApplicationContext());

        TextView nomeUsuarioTextView = (TextView) header.findViewById(R.id.nomeMenu);
        nomeUsuarioTextView.setText(loginSharedPreferences.getNome());
        TextView emailUsuarioTextView = (TextView) header.findViewById(R.id.emailMenu);
        emailUsuarioTextView.setText(loginSharedPreferences.getEmail());

        ImageButton configuracao = (ImageButton) header.findViewById(R.id.configuracaoImageButton);
        configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConfiguracaoController configuracaoController = new ConfiguracaoController();
                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.relative_layout_fragmento, configuracaoController, configuracaoController.getTag()).commit();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

    }

    public void setCheckedItem(int item){
        navigationView.setCheckedItem(item);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

                getSupportFragmentManager().popBackStackImmediate();
            }
            else{

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);

                alertDialogBuilder.setTitle("Logoff");

                alertDialogBuilder
                        .setMessage("Deseja Sair do Aplicativo ?")
                        .setCancelable(false)
                        .setPositiveButton("Sim",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                Intent intent = new Intent(MenuPrincipal.this,
                                        LoginController.class);
                                startActivity(intent);
                                LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(getApplicationContext());
                                loginSharedPreferences.setUsuarioLogado(null, null, null);


                                finish();

                            }
                        })
                        .setNegativeButton("Não",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // se não for precionado ele apenas termina o dialog
                                // e fecha a janelinha
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return false;
        }

        if (id == R.id.check) {
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();


        if (id == R.id.nav_home) {
            HomeController homeController = new HomeController();
            manager.beginTransaction().replace(R.id.relative_layout_fragmento, homeController, homeController.getTag()).commit();
        }
        else if (id == R.id.nav_clientes) {
            ClienteController clienteController = new ClienteController();

            manager.beginTransaction().replace(R.id.relative_layout_fragmento, clienteController, clienteController.getTag()).commit();
        }
        else if (id == R.id.nav_produtos) {
            ProdutoController produtoController = new ProdutoController();

            manager.beginTransaction().replace(R.id.relative_layout_fragmento, produtoController, produtoController.getTag()).commit();
        }
        else if (id == R.id.nav_pedidos) {
            PedidoController pedidoController= new PedidoController();

            manager.beginTransaction().replace(R.id.relative_layout_fragmento, pedidoController, pedidoController.getTag()).commit();
        }
        else if(id == R.id.nav_redes_sociais){
            RedeSocialController redeSocialController= new RedeSocialController();

            manager.beginTransaction().replace(R.id.relative_layout_fragmento, redeSocialController, redeSocialController.getTag()).commit();
        }


        /*else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
