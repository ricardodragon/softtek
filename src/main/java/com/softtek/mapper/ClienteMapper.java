package com.softtek.mapper;

import com.softtek.dto.ClienteDTO;
import com.softtek.dto.ClientePostDTO;
import com.softtek.model.Cliente;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ClienteMapper {

    Cliente toModel(ClienteDTO clienteDTO);

    ClienteDTO toDTO(Cliente cliente);

    Cliente toModelSemID(ClientePostDTO clienteDTO);

}
