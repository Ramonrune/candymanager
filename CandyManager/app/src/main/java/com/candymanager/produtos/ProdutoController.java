package com.candymanager.produtos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candymanager.R;
import com.candymanager.cliente.ClienteController;
import com.candymanager.cliente.ClienteDAO;
import com.candymanager.cliente.ClienteListaView;
import com.candymanager.cliente.ClienteModel;
import com.candymanager.cliente.alterar.ClienteAlteraController;
import com.candymanager.cliente.cadastrar.ClienteAdicionaController;
import com.candymanager.cliente.mostrar.ClienteMostraController;
import com.candymanager.menu.MenuPrincipal;
import com.candymanager.produtos.alterar.ProdutoAlteraController;
import com.candymanager.produtos.cadastrar.ProdutoAdicionaController;
import com.candymanager.produtos.mostrar.ProdutoMostraController;
import com.candymanager.produtos.recycler.ItemClickListener;
import com.candymanager.util.Mensagem;
import com.candymanager.util.Validation;

public class ProdutoController extends Fragment {

    private ProdutoListaView produtoListaView;
    private ProdutoModel produtoModel;
    private ProdutoDAO produtoDAO;
    private Validation validacao = new Validation();
    private View view;

    public ProdutoController() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        inicializaRecursos(inflater, container);
        inicializaListeners();

        ((MenuPrincipal) getActivity())
                .setActionBarTitle("Produtos");


        return view;
    }

    private void inicializaListeners() {
        produtoListaView.getNovoProdutoFloatingActionButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProdutoAdicionaController produtoController = new ProdutoAdicionaController();
                android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();

                manager.beginTransaction().replace(R.id.relative_layout_fragmento, produtoController, produtoController.getTag()).addToBackStack(null).commit();


            }
        });

        produtoListaView.getProdutoAdapter().setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {


                final ProdutoModel model = produtoListaView.getProdutoAdapter().getItem(position);

                System.out.println((model.getNome()) + "-------------");
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Opções")
                        .setItems(R.array.menu_options, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int posicao) {


                                if(posicao == 0){
                                    ProdutoMostraController clienteController = new ProdutoMostraController();
                                    clienteController.setProdutoModel(model);

                                    android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
                                    manager.beginTransaction().replace(R.id.relative_layout_fragmento, clienteController, clienteController.getTag()).addToBackStack(null).commit();
                                }
                                else if(posicao == 1){
                                    ProdutoAlteraController produtoController = new ProdutoAlteraController();
                                    produtoController.setProdutoModel(model);

                                    android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
                                    manager.beginTransaction().replace(R.id.relative_layout_fragmento, produtoController, produtoController.getTag()).addToBackStack(null).commit();

                                }
                                else if(posicao == 2){
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setTitle("Excluir");
                                    builder.setMessage("Deseja realmente excluir o produto " + model.getNome() + " ?")
                                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    boolean success = produtoDAO.excluir(model);

                                                    ProdutoController controller = new ProdutoController();

                                                    if(success){
                                                        Mensagem.mostrarDialogoMudarFragmento(controller, getActivity(), "Sucesso", "Produto removido com sucesso!");
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
        view = inflater.inflate(R.layout.fragment_produto, container, false);
        produtoDAO = new ProdutoDAO(container.getContext());
        produtoListaView = new ProdutoListaView(view, produtoDAO.getLista());
        produtoModel = new ProdutoModel();



    }




}
