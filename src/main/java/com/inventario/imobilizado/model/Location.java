package com.inventario.imobilizado.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "localizacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idLocalizacao")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localizacao")
    private int idLocalizacao;
    private String nome;

    @Override
    public String toString() {
        return nome;
    }

    public void setName(String locationName) {
        this.nome = locationName;
    }

    public Location(String locationName) {
        this.nome = locationName;
    }
}
