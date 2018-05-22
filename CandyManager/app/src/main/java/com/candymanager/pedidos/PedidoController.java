package com.candymanager.pedidos;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.candymanager.R;
import com.candymanager.menu.MenuPrincipal;
import com.candymanager.pedidos.cadastrar.PedidoAdicionaController;
import com.candymanager.pedidos.mostrar.PedidoMostraController;
import com.candymanager.produtos.ProdutoController;
import com.candymanager.produtos.alterar.ProdutoAlteraController;
import com.candymanager.produtos.cadastrar.ProdutoAdicionaController;
import com.candymanager.produtos.mostrar.ProdutoMostraController;
import com.candymanager.produtos.recycler.ItemClickListener;
import com.candymanager.util.Mensagem;
import com.candymanager.util.Validation;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class PedidoController extends Fragment {

    private PedidoListaView pedidoListaView;
    private PedidoModel pedidoModel;
    private PedidoDAO pedidoDAO;
    private Validation validacao = new Validation();
    private View view;

    public PedidoController() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        ((MenuPrincipal) getActivity())
                .setActionBarTitle("Pedidos");
        inicializaRecursos(inflater, container);
        inicializaListeners();
        return view;
    }


    private void inicializaListeners() {
        pedidoListaView.getNovoPedidooFloatingActionButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("TESTEEEE ");
                Log.d("testeee", "testeeeeee");
                PedidoAdicionaController pedidoAdicionaController = new PedidoAdicionaController();
                android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();

                manager.beginTransaction().replace(R.id.relative_layout_fragmento, pedidoAdicionaController, pedidoAdicionaController.getTag()).addToBackStack(null).commit();


            }
        });

        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

        List<CalendarEvent> eventList = new ArrayList<>();
        inicializaLista(eventList, view.getContext());

        pedidoListaView.getCalendarioAgendaCalendarView().init(eventList, minDate, maxDate, Locale.getDefault(), new CalendarPickerController() {
            @Override
            public void onDaySelected(DayItem dayItem) {

            }

            @Override
            public void onEventSelected(CalendarEvent event) {

                System.out.println(event.getId());
                if(event.getId() != 0){
                    final PedidoModel model = getPedido(event.getId());

                    System.out.println(model.getIdPedido() + " ---- " + model.getCliente());

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Opções")
                            .setItems(R.array.menu_options, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int posicao) {


                                    if(posicao == 0){
                                        PedidoMostraController pedidoMostraController = new PedidoMostraController();
                                        pedidoMostraController.setPedidoModel(model);

                                        android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
                                        manager.beginTransaction().replace(R.id.relative_layout_fragmento, pedidoMostraController, pedidoMostraController.getTag()).addToBackStack(null).commit();
                                    }
                                    else if(posicao == 1){
                                        //ProdutoAlteraController produtoController = new ProdutoAlteraController();
                                        //produtoController.setProdutoModel(model);

                                        //android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
                                        //manager.beginTransaction().replace(R.id.relative_layout_fragmento, produtoController, produtoController.getTag()).addToBackStack(null).commit();

                                    }
                                    else if(posicao == 2){
                                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        builder.setTitle("Excluir");
                                        builder.setMessage("Deseja realmente excluir o evento da entrega para " + model.getCliente() + " ?")
                                                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {

                                                   boolean success = pedidoDAO.excluir(model);

                                                    PedidoController controller = new PedidoController();

                                                    if(success){
                                                        Mensagem.mostrarDialogoMudarFragmento(controller, getActivity(), "Sucesso", "Pedido removido com sucesso!");
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

            }

            @Override
            public void onScrollToDate(Calendar calendar) {

            }
        });

        /*
        pedidoListaView.getPedidoAdapter().setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {


                final PedidoModel model = pedidoListaView.getPedidoAdapter().getItem(position);

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

                                                    boolean success = pedidoDAO.excluir(model);

                                                    PedidoController controller = new PedidoController();

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

        */



    }

    private void inicializaLista(List<CalendarEvent> eventList, Context context) {


        int i = 1;
        for(PedidoModel model : listaPedidos){

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(model.getData());

            System.out.println(new Date(model.getData()).toString());

            BaseCalendarEvent event1 = new BaseCalendarEvent("Entrega para " + model.getCliente(), "Entrega", model.getEndereco(),
                    ContextCompat.getColor(context, R.color.blue_selected), calendar, calendar, true);

            model.setIdLista(i);
            event1.setId(i);
            eventList.add(event1);
            i++;
        }


    }
    private ArrayList<PedidoModel> listaPedidos;
    private void inicializaRecursos(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_pedido, container, false);
        pedidoDAO = new PedidoDAO(container.getContext());
        pedidoListaView = new PedidoListaView(view, pedidoDAO.getLista());
        pedidoModel = new PedidoModel();
        listaPedidos = pedidoDAO.getLista();



    }

    private PedidoModel getPedido(long id){
        for(PedidoModel pedido : listaPedidos){
            if(pedido.getIdLista() == id){
                return pedido;
            }
        }

        return null;
    }


}
