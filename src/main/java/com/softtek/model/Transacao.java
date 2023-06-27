package com.softtek.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(exclude = "cliente")
@NoArgsConstructor
@AllArgsConstructor
public class Transacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_cliente")
    private Long idCliente;
    @Enumerated(EnumType.ORDINAL)
    private TipoTransacao tipo;
    @Column(name = "valor", precision = 16, scale=2)
    private BigDecimal valor;
    @Column(name = "taxa", precision = 16, scale=2)
    private BigDecimal taxa;
    private Date data;

    @MapsId("id_cliente")
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente", nullable = false, referencedColumnName = "id")
    private Cliente cliente;

}
