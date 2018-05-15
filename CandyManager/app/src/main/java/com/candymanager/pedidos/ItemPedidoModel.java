package com.candymanager.pedidos;

public class ItemPedidoModel {

    private String idItemPedido;
    private String doce;
    private int quantidade;
    private float valor_total_gasto;
    private int margemLucro;

    public String getIdItemPedido() {
        return idItemPedido;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public float getValor_total_gasto() {
        return valor_total_gasto;
    }

    public int getMargemLucro() {
        return margemLucro;
    }

    public String getDoce() {
        return doce;
    }
}
