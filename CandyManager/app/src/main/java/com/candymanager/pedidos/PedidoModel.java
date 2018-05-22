package com.candymanager.pedidos;

import com.candymanager.cliente.ClienteModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PedidoModel {

    private long idLista;
    private String idPedido;
    private ClienteModel cliente;
    private String endereco;
    private String cep;
    private String numero;
    private String bairro;

    private String margemLucro;
    private String precoVenda;
    private String valorTotalGasto;

    private long data;

    private List<ItemPedidoModel> listaItemsDePedido = new ArrayList<>();

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedidoModel> getListaItemsDePedido() {
        return listaItemsDePedido;
    }

    public void adicionaItemPedido(ItemPedidoModel itemPedidoModel){
        listaItemsDePedido.add(itemPedidoModel);
    }

    public long getIdLista() {
        return idLista;
    }

    public void setIdLista(long idLista) {
        this.idLista = idLista;
    }
}
