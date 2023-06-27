package com.softtek.mapper;

import com.softtek.dto.ClienteDTO;
import com.softtek.dto.ClientePostDTO;
import com.softtek.model.Cliente;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ClienteMapperTest {

    @InjectMocks
    ClienteMapperImpl clienteMapper;

    private Cliente cliente = new Cliente(1L,"Ricardo",true, BigDecimal.valueOf(335000), "2135456", Mockito.mock(Date.class), new HashSet<>());
    private ClienteDTO clienteDTO = new ClienteDTO(1L,"Ricardo",true,BigDecimal.valueOf(335000), "2135456", Mockito.mock(Date.class));
    private ClientePostDTO clientePostDTO = new ClientePostDTO("Ricardo",true,BigDecimal.valueOf(335000), "2135456", Mockito.mock(Date.class));

    @Test
    public void verificaToDTO() throws Exception{
        ClienteDTO c = clienteMapper.toDTO(null);
        assertThat(c).isSameAs(null);
        c = clienteMapper.toDTO(cliente);
        assertThat(c.getId()).isSameAs(cliente.getId());
    }

    @Test
    public void verificaToModel() throws Exception{
        Cliente c = clienteMapper.toModel(null);
        assertThat(c).isSameAs(null);
        c = clienteMapper.toModel(clienteDTO);
        assertThat(c.getId()).isSameAs(cliente.getId());
    }

    @Test
    public void verificaToModelSemId() throws Exception{
        Cliente c = clienteMapper.toModelSemID(null);
        assertThat(c).isSameAs(null);
        c = clienteMapper.toModelSemID(clientePostDTO);
        assertThat(c.getNome()).isSameAs(cliente.getNome());
    }

}
