package com.candymanager.cliente;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.candymanager.R;
import com.candymanager.cadastro.CadastroController;
import com.candymanager.cadastro.CadastroDAO;
import com.candymanager.cliente.alterar.ClienteAlteraController;
import com.candymanager.cliente.cadastrar.ClienteAdicionaController;
import com.candymanager.cliente.cadastrar.ClienteAdicionaView;
import com.candymanager.cliente.mostrar.ClienteMostraController;
import com.candymanager.cliente.mostrar.ClienteMostraView;
import com.candymanager.cliente.recycler.ItemClickListener;
import com.candymanager.db.ClienteContrato;
import com.candymanager.menu.MenuPrincipal;
import com.candymanager.util.Mensagem;
import com.candymanager.util.Validation;

import java.util.LinkedHashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClienteController extends Fragment {


    private ClienteListaView clienteView;
    private ClienteModel clienteModel;
    private ClienteDAO clienteDAO;

    private Validation validacao = new Validation();

    public ClienteController() {
    }


    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        inicializaRecursos(inflater, container);
        inicializaListeners();

        ((MenuPrincipal) getActivity())
                .setActionBarTitle("Clientes");


        return view;

    }

    private void inicializaListeners() {
        clienteView.getNovoClienteFloatingActionButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClienteAdicionaController clienteController = new ClienteAdicionaController();
                android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();

                manager.beginTransaction().replace(R.id.relative_layout_fragmento, clienteController, clienteController.getTag()).addToBackStack(null).commit();


            }
        });

        clienteView.getClienteAdapter().setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {

                final ClienteModel model = clienteView.getClienteAdapter().getItem(position);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Opções")
                        .setItems(R.array.menu_options, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int posicao) {


                                if(posicao == 0){
                                    ClienteMostraController clienteController = new ClienteMostraController();
                                    clienteController.setClienteModel(model);

                                    android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
                                    manager.beginTransaction().replace(R.id.relative_layout_fragmento, clienteController, clienteController.getTag()).addToBackStack(null).commit();
                                }
                                else if(posicao == 1){
                                    ClienteAlteraController clienteController = new ClienteAlteraController();
                                    clienteController.setClienteModel(model);

                                    android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
                                    manager.beginTransaction().replace(R.id.relative_layout_fragmento, clienteController, clienteController.getTag()).addToBackStack(null).commit();

                                }
                                else if(posicao == 2){
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setTitle("Excluir");
                                    builder.setMessage("Deseja realmente excluir o cliente " + model.getNome() + " ?")
                                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    boolean success = clienteDAO.excluir(model);

                                                    ClienteController controller = new ClienteController();

                                                    if(success){
                                                        Mensagem.mostrarDialogoMudarFragmento(controller, getActivity(), "Sucesso", "Cliente removido com sucesso!");
                                                    }
                                                    else{
                                                        Mensagem.mostrarDialogoMudarFragmento(controller, getActivity(), "Erro", "Ocorreu um erro durante a remoção, por favor, tente novamente ou entre em contato com o suporte!");
                                                    }

                                                }
                                            })
                                            .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    builder.create().dismiss();
                                                }
                                            });

                                    builder.show();
                                }


                            }
                        });

                builder.show();

            }
        });


    }




    private void inicializaRecursos(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_cliente, container, false);
        clienteDAO = new ClienteDAO(container.getContext());
        clienteView = new ClienteListaView(view, clienteDAO.getLista());

        clienteModel = new ClienteModel();



    }





}
