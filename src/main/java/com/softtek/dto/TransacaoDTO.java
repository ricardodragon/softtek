package com.softtek.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransacaoDTO {

    private Long id;
    private Long idCliente;
    @Enumerated(EnumType.ORDINAL)
    private TipoTransacao tipo;
    private BigDecimal valor;
    private Date data;

    private enum TipoTransacao {
        SAQUE, DEPOSITO
    }
}
