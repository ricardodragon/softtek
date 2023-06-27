package com.softtek.mapper;

import com.softtek.dto.TransacaoDTO;
import com.softtek.model.TipoTransacao;
import com.softtek.model.Transacao;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TransacaoMapperTest {

    @InjectMocks
    TransacaoMapperImpl transacaoMapper;
    private TransacaoDTO transacaoDTO = new TransacaoDTO(1L,1L, TipoTransacao.DEPOSITO,BigDecimal.valueOf(335000), BigDecimal.valueOf(5), Mockito.mock(Date.class));
    private Transacao transacao = new Transacao(1L,1L,TipoTransacao.DEPOSITO, BigDecimal.valueOf(335000), BigDecimal.valueOf(5), Mockito.mock(Date.class), null);


    @Test
    public void verificaToDTO() throws Exception{
        TransacaoDTO t = transacaoMapper.toDTO(null);
        assertThat(t).isSameAs(null);
        t = transacaoMapper.toDTO(transacao);
        assertThat(t.getId()).isSameAs(transacao.getId());
    }

    @Test
    public void verificaToModel() throws Exception{
        Transacao t = transacaoMapper.toModel(null);
        assertThat(t).isSameAs(null);
        t = transacaoMapper.toModel(transacaoDTO);
        assertThat(t.getId()).isSameAs(transacao.getId());
    }
}
