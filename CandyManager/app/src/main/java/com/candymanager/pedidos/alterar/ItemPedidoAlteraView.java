package com.candymanager.pedidos.alterar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.candymanager.R;
import com.candymanager.produtos.ProdutoModel;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

/**
 * Created by Usuario on 18/05/2018.
 */

public class ItemPedidoAlteraView {

    private TextView        numeroItemPedido;
    private Button          doceButton;
    private DiscreteSeekBar quantidadeDoceDiscreteSeekBar;
    private EditText        precoDeVendaEditText;
    private EditText        valorTotalDosGastosEditText;
    private DiscreteSeekBar margemDeLucroDiscreteSeekBar;
    private ImageButton     excluirItemPedidoImageButton;
    private ProdutoModel    produtoModel;


    public ItemPedidoAlteraView(View view){

        numeroItemPedido = (TextView) view.findViewById(R.id.numeroItemPedidoTextView);
        doceButton = (Button) view.findViewById(R.id.doceButton);
        precoDeVendaEditText = (EditText) view.findViewById(R.id.precoDeVendaEditText);
        quantidadeDoceDiscreteSeekBar = (DiscreteSeekBar) view.findViewById(R.id.quantidadeDoceDiscreteSeekBar);
        valorTotalDosGastosEditText = (EditText) view.findViewById(R.id.valorTotalDosGastosEditText);
        margemDeLucroDiscreteSeekBar = (DiscreteSeekBar)  view.findViewById(R.id.margemDeLucroDiscreteSeekBar);
        excluirItemPedidoImageButton = (ImageButton)  view.findViewById(R.id.excluirItemPedidoImageButton);

    }

    public TextView getNumeroItemPedido() {
        return numeroItemPedido;
    }

    public void setNumeroItemPedido(TextView numeroItemPedido) {
        this.numeroItemPedido = numeroItemPedido;
    }



    public Button getDoceButton() {
        return doceButton;
    }

    public void setDoceButton(Button doceButton) {
        this.doceButton = doceButton;
    }

    public void setQuantidadeDoceDiscreteSeekBar(DiscreteSeekBar quantidadeDoceDiscreteSeekBar) {
        this.quantidadeDoceDiscreteSeekBar = quantidadeDoceDiscreteSeekBar;
    }

    public void setMargemDeLucroDiscreteSeekBar(DiscreteSeekBar margemDeLucroDiscreteSeekBar) {
        this.margemDeLucroDiscreteSeekBar = margemDeLucroDiscreteSeekBar;
    }

    public void setExcluirItemPedidoImageButton(ImageButton excluirItemPedidoImageButton) {
        this.excluirItemPedidoImageButton = excluirItemPedidoImageButton;
    }

    public DiscreteSeekBar getQuantidadeDoceDiscreteSeekBar() {
        return quantidadeDoceDiscreteSeekBar;
    }

    public EditText getPrecoDeVendaEditText() {
        return precoDeVendaEditText;
    }

    public void setPrecoDeVendaEditText(EditText precoDeVendaEditText) {
        this.precoDeVendaEditText = precoDeVendaEditText;
    }

    public EditText getValorTotalDosGastosEditText() {
        return valorTotalDosGastosEditText;
    }

    public void setValorTotalDosGastosEditText(EditText valorTotalDosGastosEditText) {
        this.valorTotalDosGastosEditText = valorTotalDosGastosEditText;
    }

    public DiscreteSeekBar getMargemDeLucroDiscreteSeekBar() {
        return margemDeLucroDiscreteSeekBar;
    }

    public ImageButton getExcluirItemPedidoImageButton() {
        return excluirItemPedidoImageButton;
    }

    public ProdutoModel getProdutoModel() {
        return produtoModel;
    }

    public void setProdutoModel(ProdutoModel produtoModel) {
        this.produtoModel = produtoModel;
    }
}
