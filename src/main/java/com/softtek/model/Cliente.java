package com.softtek.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Boolean planoExclusive;
    @Column(name = "saldo", precision = 16, scale=2)
    private BigDecimal saldo;
    @Column(unique = true)
    private String numeroConta;
    private Date dataNascimento;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<Transacao> transacoes = new HashSet<>();

}
