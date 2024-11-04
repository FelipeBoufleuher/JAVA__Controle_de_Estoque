package com.inventario.imobilizado.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idItem")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItem;
    private String descricao;
    private String localizacaoAtual;
    private Integer potencia;
    private String patrimonio;
    private String numeroDeSerie;
    private String numeroNotaFiscal;
    private String comentarioManutencao;
    private String estado;

    @Temporal(TemporalType.DATE)
    private Date dataEntrada;
    @Temporal(TemporalType.DATE)
    private Date ultimaQualificacao;
    @Temporal(TemporalType.DATE)
    private Date dataNotaFiscal;
    @Temporal(TemporalType.DATE)
    private Date proximaQualificacao;
    @Temporal(TemporalType.DATE)
    private Date prazoManutencao;

    @ManyToOne
    @JoinColumn(name = "categoria_id_categoria")
    private Category categoria;
    @Column(name = "status_item")
    private String status;
    @ManyToOne
    @JoinColumn(name = "modelo_id_modelo")
    private Modelo modelo;
    @ManyToOne
    @JoinColumn(name = "localizacao_id_localizacao")
    private Location localizacao;

}
