package com.candymanager.pedidos.alterar;

import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.candymanager.R;

public class PedidoAlteraView {

    private EditText        clienteEditText;
    private EditText        quantidadeDoceEditText;
    private EditText        precoDeVendaEditText;
    private EditText        valorTotalDosGastosEditText;
    private EditText        cepEditText;
    private EditText        enderecoEditText;
    private EditText        bairroEditText;
    private EditText        numeroEditText;

    private ImageButton     acrescentarDoceImageButton;
    private ImageButton     decresmentarDoceImageButton;
    private ImageButton     agendaImageButton;

    private Spinner         margemDeLucroSpinner;
    private Spinner         doceSpinner;

    private CalendarView    calendarView;

    public PedidoAlteraView(View view) {

        clienteEditText = (EditText) view.findViewById(R.id.clienteEditText);
        quantidadeDoceEditText = (EditText) view.findViewById(R.id.quantidadeDoceEditText);
        precoDeVendaEditText = (EditText) view.findViewById(R.id.precoDeVendaEditText);
        valorTotalDosGastosEditText = (EditText) view.findViewById(R.id.valorTotalDosGastosEditText);
        cepEditText = (EditText) view.findViewById(R.id.cepEditText);
        enderecoEditText = (EditText) view.findViewById(R.id.enderecoEditText);
        bairroEditText = (EditText) view.findViewById(R.id.bairroEditText);
        numeroEditText = (EditText) view.findViewById(R.id.numeroEditText);

        acrescentarDoceImageButton = (ImageButton) view.findViewById(R.id.acrescentaDoceImageButton);
        decresmentarDoceImageButton = (ImageButton) view.findViewById(R.id.decresmentaDoceImageButton);
        agendaImageButton = (ImageButton) view.findViewById(R.id.agendaImageButton);

        margemDeLucroSpinner = (Spinner)  view.findViewById(R.id.margemDeLucroSpinner);
        doceSpinner = (Spinner) view.findViewById(R.id.doceSpinner);

        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
    }

    public EditText getClienteEditText() {
        return clienteEditText;
    }

    public EditText getQuantidadeDoceEditText() {
        return quantidadeDoceEditText;
    }

    public EditText getPrecoDeVendaEditText() {
        return precoDeVendaEditText;
    }

    public EditText getValorTotalDosGastosEditText() {
        return valorTotalDosGastosEditText;
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

    public ImageButton getAcrescentarDoceImageButton() {
        return acrescentarDoceImageButton;
    }

    public ImageButton getDecresmentarDoceImageButton() {
        return decresmentarDoceImageButton;
    }

    public ImageButton getAgendaImageButton() {
        return agendaImageButton;
    }

    public Spinner getMargemDeLucroSpinner() {
        return margemDeLucroSpinner;
    }

    public Spinner getDoceSpinner() {
        return doceSpinner;
    }

    public CalendarView getCalendarView() {
        return calendarView;
    }
}
