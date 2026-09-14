package br.com.aula.techx.model;

public class Tarefas {

    private String nome;

    private String descricao;

    private String lembrete;

    public Tarefas(String nome, String descricao, String lembrete) {
        this.nome = nome;
        this.descricao = descricao;
        this.lembrete = lembrete;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setLembrete(String lembrete) {
        this.lembrete = lembrete;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

