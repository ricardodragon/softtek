package com.softtek.service;

import com.softtek.dto.ClienteDTO;
import com.softtek.dto.ClientePostDTO;
import com.softtek.dto.TransacaoDTO;
import com.softtek.mapper.ClienteMapper;
import com.softtek.mapper.TransacaoMapper;
import com.softtek.model.Cliente;
import com.softtek.model.TipoTransacao;
import com.softtek.model.Transacao;
import com.softtek.repository.ClienteRepository;
import com.softtek.repository.TransacaoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {

    @Mock
    ClienteRepository clienteRepository;
    @Mock
    TransacaoRepository transacaoRepository;
    @Mock
    private ClienteMapper clienteMapper;
    @Mock
    private TransacaoMapper transacaoMapper;
    @InjectMocks
    ClienteService clienteService;

    private List<Cliente> clientes = Arrays.asList(
            new Cliente(1L,"Ricardo",true, BigDecimal.valueOf(335000), "2135456", Mockito.mock(Date.class), new HashSet<>()),
            new Cliente(2L,"Homer",true,BigDecimal.valueOf(335000), "864545",Mockito.mock(Date.class), new HashSet<>()),
            new Cliente(3L,"Bart",false,BigDecimal.valueOf(335000), "1321635",Mockito.mock(Date.class), new HashSet<>())
    );
    private Cliente cliente = new Cliente(1L,"Ricardo",true,BigDecimal.valueOf(335000), "2135456", Mockito.mock(Date.class), new HashSet<>());
    private Cliente cliente2 = new Cliente(2L,"864545",false,BigDecimal.valueOf(335000), "2135456", Mockito.mock(Date.class), new HashSet<>());
    private List<Transacao> transacoes = Arrays.asList(
            new Transacao(1L,1L, TipoTransacao.DEPOSITO,BigDecimal.valueOf(335000), BigDecimal.valueOf(5), Mockito.mock(Date.class), null),
            new Transacao(2L,1L, TipoTransacao.SAQUE,BigDecimal.valueOf(335000), BigDecimal.valueOf(3),Mockito.mock(Date.class), null),
            new Transacao(3L,2L, TipoTransacao.DEPOSITO,BigDecimal.valueOf(335000), BigDecimal.valueOf(4),Mockito.mock(Date.class), null),
            new Transacao(4L,3L, TipoTransacao.SAQUE,BigDecimal.valueOf(335000), BigDecimal.valueOf(4),Mockito.mock(Date.class), null)
    );
    private List<ClienteDTO> clientesDTO = Arrays.asList(
            new ClienteDTO(1L,"Ricardo",true,BigDecimal.valueOf(335000), "2135456", Mockito.mock(Date.class)),
            new ClienteDTO(2L,"Homer",true,BigDecimal.valueOf(335000), "864545",Mockito.mock(Date.class)),
            new ClienteDTO(3L,"Bart",false,BigDecimal.valueOf(335000), "1321635",Mockito.mock(Date.class))
    );
    private ClienteDTO clienteDTO = new ClienteDTO(1L,"Ricardo",true,BigDecimal.valueOf(335000), "2135456", Mockito.mock(Date.class));
    private ClientePostDTO clientePostDTO = new ClientePostDTO("Ricardo",true,BigDecimal.valueOf(335000), "2135456", Mockito.mock(Date.class));
    TransacaoDTO transacaoDTO = new TransacaoDTO(1L,1L, TipoTransacao.DEPOSITO,BigDecimal.valueOf(335000), BigDecimal.valueOf(5), Mockito.mock(Date.class));
    private List<TransacaoDTO> transacoesDTO = Arrays.asList(
            new TransacaoDTO(1L,1L, TipoTransacao.DEPOSITO,BigDecimal.valueOf(335000), BigDecimal.valueOf(5), Mockito.mock(Date.class)),
            new TransacaoDTO(2L,1L, TipoTransacao.SAQUE,BigDecimal.valueOf(335000), BigDecimal.valueOf(3),Mockito.mock(Date.class)),
            new TransacaoDTO(3L,2L, TipoTransacao.DEPOSITO,BigDecimal.valueOf(335000), BigDecimal.valueOf(4),Mockito.mock(Date.class)),
            new TransacaoDTO(4L,3L, TipoTransacao.SAQUE,BigDecimal.valueOf(335000), BigDecimal.valueOf(4),Mockito.mock(Date.class))
    );

    @Test
    public void verificaRetornarClientes() throws Exception{
        when(clienteRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(new PageImpl<>(clientes, PageRequest.of(0, 20), clientes.size()));
        when(clienteMapper.toDTO(Mockito.any(Cliente.class))).thenReturn(clienteDTO);
        Page<ClienteDTO> c = clienteService.getAll(PageRequest.of(0, 20));
        assertThat(clientes.size()).isSameAs(c.getContent().size());
    }

    @Test
    public void verificaGetTransacaoByData() throws Exception{
        when(transacaoRepository.findByDataBetween(Mockito.any(Date.class), Mockito.any(Date.class), Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(transacoes, PageRequest.of(0, 20), transacoes.size()));
        when(transacaoMapper.toDTO(Mockito.any(Transacao.class))).thenReturn(transacaoDTO);
        Page<TransacaoDTO> t = clienteService.getTransacaoByData(PageRequest.of(0, 20), new Date());
        assertThat(transacoes.size()).isSameAs(t.getContent().size());
    }

    @Test
    public void verificaSaque() throws Exception{
        when(clienteRepository.findByNumeroConta(Mockito.any(String.class))).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);
        when(clienteMapper.toDTO(Mockito.any(Cliente.class))).thenReturn(clienteDTO);
        ClienteDTO c = clienteService.saque(cliente.getNumeroConta(), BigDecimal.ZERO);
        assertThat(cliente.getId()).isSameAs(c.getId());
        when(clienteRepository.findByNumeroConta(Mockito.any(String.class))).thenReturn(Optional.of(cliente2));
        when(clienteMapper.toDTO(Mockito.any(Cliente.class))).thenReturn(clienteDTO);
        c = clienteService.saque(cliente.getNumeroConta(), BigDecimal.valueOf(100));
        assertThat(cliente.getId()).isSameAs(c.getId());
        c = clienteService.saque(cliente.getNumeroConta(), BigDecimal.valueOf(200));
        assertThat(cliente.getId()).isSameAs(c.getId());
        c = clienteService.saque(cliente.getNumeroConta(), BigDecimal.valueOf(500));
        assertThat(cliente.getId()).isSameAs(c.getId());

    }

    @Test
    public void verificaDeposito() throws Exception{
        when(clienteRepository.findByNumeroConta(Mockito.any(String.class))).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);
        when(clienteMapper.toDTO(Mockito.any(Cliente.class))).thenReturn(clienteDTO);
        ClienteDTO c = clienteService.deposito(cliente.getNumeroConta(), BigDecimal.valueOf(500));
        assertThat(cliente.getId()).isSameAs(c.getId());
        assertThat(cliente.getNome()).isSameAs(c.getNome());
        c = clienteService.deposito(cliente.getNumeroConta(), BigDecimal.ZERO);
        assertThat(cliente.getId()).isSameAs(c.getId());
        assertThat(cliente.getNome()).isSameAs(c.getNome());
    }

    @Test
    public void verificaPost() throws Exception{
        when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);
        when(clienteMapper.toModelSemID(Mockito.any(ClientePostDTO.class))).thenReturn(cliente);
        when(clienteMapper.toDTO(Mockito.any(Cliente.class))).thenReturn(clienteDTO);
        ClienteDTO c = clienteService.post(clientePostDTO);
        assertThat(cliente.getId()).isSameAs(c.getId());
        assertThat(cliente.getNome()).isSameAs(c.getNome());
    }

}
