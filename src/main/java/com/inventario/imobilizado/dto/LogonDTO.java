package com.inventario.imobilizado.dto;

import java.util.Objects;

public class LogonDTO {
    private String nome;
    private String sobrenome;
    private String email;
    private String tipoUsuario;
    private String senha;
    private String confirmarSenha;

    public LogonDTO(String nome, String sobrenome, String email, String tipoUsuario, String senha, String confirmarSenha) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.senha = senha;
        this.confirmarSenha = confirmarSenha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogonDTO logonDTO = (LogonDTO) o;
        return Objects.equals(nome, logonDTO.nome) && Objects.equals(sobrenome, logonDTO.sobrenome) && Objects.equals(email, logonDTO.email) && Objects.equals(tipoUsuario, logonDTO.tipoUsuario) && Objects.equals(senha, logonDTO.senha) && Objects.equals(confirmarSenha, logonDTO.confirmarSenha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, sobrenome, email, tipoUsuario, senha, confirmarSenha);
    }
}
