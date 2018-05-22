package com.candymanager.cliente;

/**
 * Created by Usuario on 20/04/2018.
 */

public class ClienteModel {
    private String id;
    private String nome;
    private String telefone;
    private String email;
    private byte[] imagem;

    public void setId(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }


    public String toString(){
        return this.nome;
    }



}
