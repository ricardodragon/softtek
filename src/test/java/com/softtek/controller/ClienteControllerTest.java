package com.softtek.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softtek.dto.ClienteDTO;
import com.softtek.dto.ClientePostDTO;
import com.softtek.dto.TransacaoDTO;
import com.softtek.model.TipoTransacao;
import com.softtek.service.ClienteService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @MockBean
    ClienteService clienteService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    private List<ClienteDTO> clientes = Arrays.asList(
        new ClienteDTO(1L,"Ricardo",true,BigDecimal.valueOf(335000), "2135456", Mockito.mock(Date.class)),
        new ClienteDTO(2L,"Homer",true,BigDecimal.valueOf(335000), "864545",Mockito.mock(Date.class)),
        new ClienteDTO(3L,"Bart",false,BigDecimal.valueOf(335000), "1321635",Mockito.mock(Date.class))
    );

    private ClienteDTO cliente = new ClienteDTO(1L,"Ricardo",true,BigDecimal.valueOf(335000), "2135456", Mockito.mock(Date.class));
    private List<TransacaoDTO> transacoes = Arrays.asList(
        new TransacaoDTO(1L,1L, TipoTransacao.DEPOSITO,BigDecimal.valueOf(335000), BigDecimal.valueOf(5), Mockito.mock(Date.class)),
        new TransacaoDTO(2L,1L, TipoTransacao.SAQUE,BigDecimal.valueOf(335000), BigDecimal.valueOf(3),Mockito.mock(Date.class)),
        new TransacaoDTO(3L,2L, TipoTransacao.DEPOSITO,BigDecimal.valueOf(335000), BigDecimal.valueOf(4),Mockito.mock(Date.class)),
        new TransacaoDTO(4L,3L, TipoTransacao.SAQUE,BigDecimal.valueOf(335000), BigDecimal.valueOf(4),Mockito.mock(Date.class))
    );

    @Test
    public void verificaRetornarClientes() throws Exception{
        when(clienteService.getAll(Mockito.any(PageRequest.class))).thenReturn(new PageImpl<>(clientes, PageRequest.of(0, 20), clientes.size()));
        mockMvc.perform(
            MockMvcRequestBuilders.get("/")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.content", Matchers.hasSize(clientes.size())));
    }

    @Test
    public void verificaGetTransacoesByData() throws Exception{
        when(clienteService.getTransacaoByData(Mockito.any(PageRequest.class), Mockito.any(Date.class))).thenReturn(new PageImpl(transacoes, PageRequest.of(0, 20), transacoes.size()));
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/transacoes/?data=2023-06-27T02:24:49.766Z")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.content", Matchers.hasSize(transacoes.size())));
    }

    @Test
    public void verificaPost() throws Exception{
        when(clienteService.post(Mockito.any(ClientePostDTO.class))).thenReturn(cliente);
        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/")
                .content(mapper.writeValueAsString(cliente))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value(cliente.getNome()));
    }

    @Test
    public void verificaSaque() throws Exception{
        when(clienteService.saque(Mockito.any(String.class), Mockito.any(BigDecimal.class))).thenReturn(cliente);
        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/saque/2135456?valor=500")
                .content(mapper.writeValueAsString(cliente))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(cliente.getId()));
    }

    @Test
    public void verificaDeposito() throws Exception{
        when(clienteService.deposito(Mockito.any(String.class), Mockito.any(BigDecimal.class))).thenReturn(cliente);
        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/deposito/2135456?valor=500")
                .content(mapper.writeValueAsString(cliente))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(cliente.getId()));
    }

    @Test
    public void verificaSaqueException() throws Exception{
        when(clienteService.saque(Mockito.any(String.class), Mockito.any(BigDecimal.class))).thenThrow(new EntityNotFoundException());
        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/saque/2135456?valor=500")
                .content(mapper.writeValueAsString(cliente))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound())
        .andReturn();
    }

    @Test
    public void verificaDepositoException() throws Exception{
        when(clienteService.deposito(Mockito.any(String.class), Mockito.any(BigDecimal.class))).thenThrow(new EntityNotFoundException());
        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/deposito/2135456?valor=500")
                .content(mapper.writeValueAsString(cliente))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound())
        .andReturn();
    }
}
