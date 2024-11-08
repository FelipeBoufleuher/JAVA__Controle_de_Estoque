package com.inventario.imobilizado.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idCategoria")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private int idCategoria;
    private String nome;

    @Override
    public String toString() {
        return nome;
    }

    public void setName(String categoryName) {
        this.nome = categoryName;
    }

    public Category(String nome) {
        this.nome = nome;
    }
}