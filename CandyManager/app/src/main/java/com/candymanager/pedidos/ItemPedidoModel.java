package com.candymanager.pedidos;

import com.candymanager.produtos.ProdutoModel;

public class ItemPedidoModel {

    private String idItemPedido;
    private ProdutoModel doce;
    private int quantidade;
    private double valorTotalGasto;
    private int margemLucro;

    public String getIdItemPedido() {
        return idItemPedido;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValorTotalGasto() {
        return valorTotalGasto;
    }

    public int getMargemLucro() {
        return margemLucro;
    }

    public ProdutoModel getDoce() {
        return doce;
    }

    public void setIdItemPedido(String idItemPedido) {
        this.idItemPedido = idItemPedido;
    }

    public void setDoce(ProdutoModel doce) {
        this.doce = doce;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setValorTotalGasto(double valorTotalGasto) {
        this.valorTotalGasto = valorTotalGasto;
    }

    public void setMargemLucro(int margemLucro) {
        this.margemLucro = margemLucro;
    }
}
