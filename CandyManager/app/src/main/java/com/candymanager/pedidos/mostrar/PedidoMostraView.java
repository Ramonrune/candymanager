package com.candymanager.pedidos.mostrar;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.candymanager.R;
import com.candymanager.pedidos.cadastrar.PedidoItemPedidoAdicionaView;
import com.github.pinball83.maskededittext.MaskedEditText;

import java.util.ArrayList;
import java.util.List;

public class PedidoMostraView {

    private LinearLayout mainLinearLayout;
    private Button clienteButton;
    private ImageButton agendaImageButton;

    private ImageView clienteImageView;

    private EditText cepEditText;
    private EditText enderecoEditText;
    private EditText bairroEditText;
    private EditText numeroEditText;


    private DatePicker dataDatePicker;

    private List<PedidoItemPedidoAdicionaView> listaPedidos = new ArrayList<>();


    public PedidoMostraView(View view) {

        mainLinearLayout = (LinearLayout) view.findViewById(R.id.mainLinearLayout);

        clienteButton = (Button) view.findViewById(R.id.clienteButton);
        agendaImageButton = (ImageButton) view.findViewById(R.id.agendaImageButton);

        clienteImageView = (ImageView) view.findViewById(R.id.clienteImageView);

        cepEditText = (EditText) view.findViewById(R.id.cepEditText);
        enderecoEditText = (EditText) view.findViewById(R.id.enderecoEditText);
        bairroEditText = (EditText) view.findViewById(R.id.bairroEditText);
        numeroEditText = (EditText) view.findViewById(R.id.numeroEditText);


        dataDatePicker = (DatePicker) view.findViewById(R.id.dataDatePicker);

        listaPedidos.add(new PedidoItemPedidoAdicionaView(view));
        listaPedidos.clear();
    }


    public ImageView getClienteImageView() {
        return clienteImageView;
    }


    public EditText getCepEditText() {
        return cepEditText;
    }

    public EditText getEnderecoEditText() {
        return enderecoEditText;
    }

    public EditText getBairroEditText() {
        return bairroEditText;
    }

    public EditText getNumeroEditText() {
        return numeroEditText;
    }


    public DatePicker getDataDatePicker() {
        return dataDatePicker;
    }

    public LinearLayout getMainLinearLayout() {
        return mainLinearLayout;
    }

    public Button getClienteButton() {
        return clienteButton;
    }

    public View getAgendaImageButton() {
        return agendaImageButton;
    }

    public List<PedidoItemPedidoAdicionaView> getListaPedidos() {
        return listaPedidos;
    }
}
