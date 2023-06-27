package com.softtek.dto;

import com.softtek.model.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransacaoDTO {

    private Long id;
    private Long idCliente;
    private TipoTransacao tipo;
    private BigDecimal valor;
    private BigDecimal taxa;
    private Date data;

}
