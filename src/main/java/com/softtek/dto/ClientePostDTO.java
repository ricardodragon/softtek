package com.softtek.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientePostDTO {

    private String nome;
    private Boolean planoExclusive;
    private BigDecimal saldo;
    private String numeroConta;
    private Date dataNascimento;
}
