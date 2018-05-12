package com.candymanager.pedidos;

public class ItemPedidoModel {

    private String id_item_pedido;
    private int quantidade;
    private float valor_total_gasto;
    private int margem;

    public String getId_item_pedido() {
        return id_item_pedido;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public float getValor_total_gasto() {
        return valor_total_gasto;
    }

    public int getMargem() {
        return margem;
    }
}
