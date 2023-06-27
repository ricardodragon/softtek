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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final TransacaoRepository transacaoRepository;
    private final ClienteMapper clienteMapper;
    private final TransacaoMapper transacaoMapper;

    public ClienteDTO post(ClientePostDTO clientePostDTO) {
        return this.clienteMapper.toDTO(this.clienteRepository.save(this.clienteMapper.toModelSemID(clientePostDTO)));
    }

    public Page<ClienteDTO> getAll(Pageable pageable) {
        return this.clienteRepository.findAll(pageable).map(this.clienteMapper::toDTO);
    }

    public Page<TransacaoDTO> getTransacaoByData(Pageable pageable, Date data) throws EntityNotFoundException{
        return this.transacaoRepository.findByDataBetween(data, new Date(), pageable).map(this.transacaoMapper::toDTO);
    }

    public ClienteDTO saque(String numConta, BigDecimal valor) throws EntityNotFoundException{
        Cliente c = this.clienteRepository.findByNumeroConta(numConta).orElseThrow(EntityNotFoundException::new);
        BigDecimal taxa = BigDecimal.ZERO;
        if(valor.compareTo(BigDecimal.ZERO)<=0) return this.clienteMapper.toDTO(c);
        if(!c.getPlanoExclusive().booleanValue() && valor.compareTo(BigDecimal.valueOf(100))>0 && valor.compareTo(BigDecimal.valueOf(300))<=0)
            taxa = valor.multiply(BigDecimal.valueOf(0.4)).divide(BigDecimal.valueOf(100));
        else if(!c.getPlanoExclusive().booleanValue())
            taxa = valor.multiply(BigDecimal.valueOf(1)).divide(BigDecimal.valueOf(100));
        c.setSaldo(c.getSaldo().subtract(valor.add(taxa)));
        c.getTransacoes().add(new Transacao(0L, c.getId(), TipoTransacao.SAQUE, valor, taxa, new Date(), c));
        return this.clienteMapper.toDTO(this.clienteRepository.save(c));
    }

    public ClienteDTO deposito(String numConta, BigDecimal valor) throws EntityNotFoundException{
        Cliente c = this.clienteRepository.findByNumeroConta(numConta).orElseThrow(EntityNotFoundException::new);
        if(valor.compareTo(BigDecimal.ZERO)<=0) return this.clienteMapper.toDTO(c);
        c.setSaldo(c.getSaldo().add(valor));
        c.getTransacoes().add(new Transacao(0L, c.getId(), TipoTransacao.DEPOSITO, valor, BigDecimal.ZERO, new Date(), c));
        return this.clienteMapper.toDTO(this.clienteRepository.save(c));
    }
}
