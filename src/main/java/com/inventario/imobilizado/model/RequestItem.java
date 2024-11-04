package com.inventario.imobilizado.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "idItem")
public class RequestItem {
        private final String DATE_PATTERN = "yyyy-MM-dd";

        private Integer idItem;
        private String descricao;
        private Integer potencia;
        private String patrimonio;
        private String localizacaoAtual;
        private String estado;
        private Integer categoria;
        private String status;
        private String numeroDeSerie;
        private Integer modelo;
        private Integer marca;
        private Integer localizacao;
        private String numeroNotaFiscal;
        private String comentarioManutencao;

        @DateTimeFormat(pattern = DATE_PATTERN)
        private Date dataEntrada;
        @DateTimeFormat(pattern = DATE_PATTERN)
        private Date dataNotaFiscal;
        @DateTimeFormat(pattern = DATE_PATTERN)
        private Date ultimaQualificacao;
        @DateTimeFormat(pattern = DATE_PATTERN)
        private Date proximaQualificacao;
        @DateTimeFormat(pattern = DATE_PATTERN)
        private Date prazoManutencao;

        public String toJson() {
                StringBuilder json = new StringBuilder();
                json.append("{");

                json.append("\"id_item\": ").append(idItem != null ? idItem : "null").append(", ");
                json.append("\"descricao\": \"").append(descricao).append("\", ");
                json.append("\"potencia\": ").append(potencia != null ? potencia : "null").append(", ");
                json.append("\"patrimonio\": \"").append(patrimonio).append("\", ");
                json.append("\"data_entrada\": \"").append(dataEntrada != null ? new SimpleDateFormat(DATE_PATTERN).format(dataEntrada) : "null").append("\", ");
                json.append("\"data_nota_fiscal\": \"").append(dataNotaFiscal != null ? new SimpleDateFormat(DATE_PATTERN).format(dataNotaFiscal) : "null").append("\", ");
                json.append("\"localizacao_atual\": \"").append(localizacaoAtual).append("\", ");
                json.append("\"ultima_qualificacao\": \"").append(ultimaQualificacao != null ? new SimpleDateFormat(DATE_PATTERN).format(ultimaQualificacao) : "null").append("\", ");
                json.append("\"proxima_qualificacao\": \"").append(proximaQualificacao != null ? new SimpleDateFormat(DATE_PATTERN).format(proximaQualificacao) : "null").append("\", ");
                json.append("\"estado\": \"").append(estado).append("\", ");
                json.append("\"categoria\": ").append(categoria != null ? categoria : "null").append(", ");
                json.append("\"status\": \"").append(status).append("\", ");
                json.append("\"numero_de_serie\": \"").append(numeroDeSerie).append("\", ");
                json.append("\"modelo\": ").append(modelo != null ? modelo : "null").append(", ");
                json.append("\"marca\": ").append(marca != null ? marca : "null").append(", ");
                json.append("\"localizacao\": ").append(localizacao != null ? localizacao : "null").append(", ");
                json.append("\"numero_nota_fiscal\": \"").append(numeroNotaFiscal).append("\", ");
                json.append("\"comentario_manutencao\": \"").append(comentarioManutencao).append("\", ");
                json.append("\"prazo_manutencao\": \"").append(prazoManutencao != null ? new SimpleDateFormat(DATE_PATTERN).format(prazoManutencao) : "null").append("\"");

                json.append("}");
                return json.toString();
        }

}