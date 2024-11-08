package com.inventario.imobilizado.model;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "marca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idMarca")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_marca")
    private int idMarca;
    private String nome;

    @Override
    public String toString() {
        return nome;
    }

    public void setName(String brandName) {
        this.nome = brandName;
    }

    public Brand(String brandName) {
        this.nome = brandName;
    }
}