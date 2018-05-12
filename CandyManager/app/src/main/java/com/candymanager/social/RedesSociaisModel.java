package com.candymanager.social;

public class RedesSociaisModel {

    private boolean facebookAtivo;
    private boolean instagramAtivo;
    private boolean twitterAtivo;
    private String titulo;
    private String imagem;

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getImagem() {
        return imagem;
    }

    public boolean isFacebookAtivo() {
        return facebookAtivo;
    }

    public boolean isInstagramAtivo() {
        return instagramAtivo;
    }

    public boolean isTwitterAtivo() {
        return twitterAtivo;
    }

    public void setFacebookAtivo(boolean facebookAtivo) {
        this.facebookAtivo = facebookAtivo;
    }

    public void setInstagramAtivo(boolean instagramAtivo) {
        this.instagramAtivo = instagramAtivo;
    }

    public void setTwitterAtivo(boolean twitterAtivo) {
        this.twitterAtivo = twitterAtivo;
    }
}
