package com.candymanager.usuario;

public class UsuarioModel {

        private String nome;
        private String email;
        private String cep;
        private String endereco;
        private String bairro;
        private String numero;
        private byte[] foto;


        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEmail() {
        return email;
    }

        public void setEmail(String email) {
        this.email = email;
    }

        public String getCep() {
        return cep;
    }

        public void setCep(String cep) {this.cep = cep;}

        public String getEndereco() {return endereco;}

        public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

        public String getBairro() {return bairro;}

        public void setBairro(String bairro) {this.bairro = bairro;}

        public String getNumero() {
        return numero;
    }

        public void setNumero(String numero) {this.numero = numero;}

        public byte[] getFoto() {return foto;}

        public void setFoto(byte[] foto) {this.foto = foto;}




}
