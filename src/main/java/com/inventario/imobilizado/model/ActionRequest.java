package com.inventario.imobilizado.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idAcao")
@ToString
public class ActionRequest {
    private String raRna;
    private String entidade;
    private String data_emprestimo;
    private String data_devolucao;
    private int idUsuario;
    private int idItem;
    private int idLocalizacaoAtual;
    private int idAnexos;
    private boolean statusEmprestimo;
    private String estado;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date prazoManutencao;
}
