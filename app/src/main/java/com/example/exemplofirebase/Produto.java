package com.example.exemplofirebase;

public class Produto {
    private String chave;
    private String mesa;
    private String item;
    private String produto;
    private boolean atendido;
    public Produto(String chave, String mesa, String item, String produto) {
        this.chave = chave;
        this.mesa = mesa;
        this.item = item;
        this.produto = produto;
        this.atendido = false;
    }
    public Produto() {

    }

    public String getChave() {
        return this.chave;
    }
    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getMesa() {
        return this.mesa;
    }
    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getItem() {
        return this.item;
    }
    public void setItem(String item) {
        this.item = item;
    }

    public String getProduto() {
        return this.produto;
    }
    public void setProduto(String produto) {
        this.produto = produto;
    }

    public boolean isAtendido() {
        return this.atendido;
    }
    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }
}