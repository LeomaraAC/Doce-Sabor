package com.leomara.delivery.doce_sabor.domain;

import java.util.Objects;

public class Empresa {

    private Integer id;
    private String nome_fantasia;
    private String cnpj;
    private String email;
    private String senha;

    public Empresa() {}

    public Empresa(Integer id, String nome_fantasia, String cnpj, String email, String senha) {
        this.id = id;
        this.nome_fantasia = nome_fantasia;
        this.cnpj = cnpj;
        this.email = email;
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return id.equals(empresa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
