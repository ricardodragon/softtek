package com.softtek.mapper;

import com.softtek.dto.TransacaoDTO;
import com.softtek.model.Transacao;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TransacaoMapper {

    Transacao toModel(TransacaoDTO transacaoDTO);

    TransacaoDTO toDTO(Transacao transacao);

}
