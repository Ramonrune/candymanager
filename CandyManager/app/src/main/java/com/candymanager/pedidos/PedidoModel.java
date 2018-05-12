package com.candymanager.pedidos;

import java.util.Date;

public class PedidoModel {

    private String idPedido;
    private Date data;
    private String endereco;
    private String cep;
    private String numero;
    private String bairro;

    public long getData() {
        return data.getTime();
    }

    public void setData(Long data) {
        this.data = new Date(data);
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

}
