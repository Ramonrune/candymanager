package com.candymanager.pedidos.mostrar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.candymanager.R;
import com.candymanager.menu.MenuPrincipal;
import com.candymanager.pedidos.ItemPedidoModel;
import com.candymanager.pedidos.PedidoModel;
import com.candymanager.util.BitmapUtil;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class PedidoMostraController extends Fragment {


    private PedidoMostraView pedidoMostraView;
    private PedidoModel pedidoModel;
    private View view;

    public PedidoMostraController() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(false);

        ((MenuPrincipal) getActivity())
                .setActionBarTitle("Pedido");
        inicializaRecursos(inflater, container);

        pedidoMostraView.getClienteImageView().setImageBitmap(BitmapUtil.getImage(pedidoModel.getCliente().getImagem()));
        Date date = new Date(pedidoModel.getData());
        pedidoMostraView.getDataDatePicker().updateDate((date.getYear() + 1900), date.getMonth(), date.getDate());

        pedidoMostraView.getClienteButton().setText(pedidoModel.getCliente().getNome());
        pedidoMostraView.getCepEditText().setText(pedidoModel.getCep());
        pedidoMostraView.getEnderecoEditText().setText(pedidoModel.getEndereco());
        pedidoMostraView.getBairroEditText().setText(pedidoModel.getBairro());
        pedidoMostraView.getNumeroEditText().setText(pedidoModel.getNumero());


        for(int i = 0; i < pedidoModel.getListaItemsDePedido().size(); i++){

            ItemPedidoModel itemPedidoModel = pedidoModel.getListaItemsDePedido().get(i);

            View newLayout = getLayoutInflater().inflate(R.layout.layout_item_pedido, null, false);
            LinearLayout ll = (LinearLayout) newLayout.findViewById(R.id.pedidoLinearLayout);
            TextView numeroPedidoTextView = (TextView) newLayout.findViewById(R.id.numeroItemPedidoTextView);
            numeroPedidoTextView.setText((i + 1) + "º item de pedido");

            Button doce = (Button) newLayout.findViewById(R.id.doceButton);
            doce.setText(itemPedidoModel.getDoce().getNome());


            DiscreteSeekBar discreteSeekBar = (DiscreteSeekBar) newLayout.findViewById(R.id.quantidadeDoceDiscreteSeekBar);
            discreteSeekBar.setProgress(itemPedidoModel.getQuantidade());

            EditText valorTotal = (EditText) newLayout.findViewById(R.id.valorTotalDosGastosEditText);
            valorTotal.setText("R$ " + String.format("%.2f", itemPedidoModel.getValorTotalGasto()));

            DiscreteSeekBar margemDeLucro = (DiscreteSeekBar) newLayout.findViewById(R.id.margemDeLucroDiscreteSeekBar);
            margemDeLucro.setProgress(itemPedidoModel.getMargemLucro());

            EditText precoDeVenda = (EditText) newLayout.findViewById(R.id.precoDeVendaEditText);

            double valorDeVenda = itemPedidoModel.getValorTotalGasto() * ((float)itemPedidoModel.getMargemLucro() / 100.00);

            precoDeVenda.setText("R$ " + String.format("%.2f", (itemPedidoModel.getValorTotalGasto() + valorDeVenda)));


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            layoutParams.setMargins(15, 15, 15, 15);
            ll.setLayoutParams(layoutParams);

            pedidoMostraView.getMainLinearLayout().addView(newLayout, 2);

        }


        return view;
    }

    private void inicializaRecursos(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_pedido_mostra, container, false);
        this.pedidoMostraView = new PedidoMostraView(view);

    }

    public void setPedidoModel(PedidoModel pedidoModel) {
        this.pedidoModel = pedidoModel;
    }

}
