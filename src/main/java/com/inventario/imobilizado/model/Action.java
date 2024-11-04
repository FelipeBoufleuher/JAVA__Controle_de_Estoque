package com.inventario.imobilizado.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "acao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idAcao")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acao")
    private int idAcao;

    private String entidade;

    @Column(name = "ra_rna")
    private String raRna;

    @Temporal(TemporalType.DATE)
    private String dataEmprestimo;

    @Temporal(TemporalType.DATE)
    private String dataDevolucao;

    @ManyToOne
    @JoinColumn(name = "usuario_id_usuario")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "item_id_item")
    private Item item;

    private int tipoAcao;

    @ManyToOne
    @JoinColumn(name = "anexos_id_anexos")
    private Attachment anexos;

    private int localizacaoIdLocalizacao;

}
