package com.softtek.service;

import com.softtek.dto.ClienteDTO;
import com.softtek.dto.ClientePostDTO;
import com.softtek.mapper.ClienteMapper;
import com.softtek.model.Cliente;
import com.softtek.model.TipoTransacao;
import com.softtek.model.Transacao;
import com.softtek.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteDTO post(ClientePostDTO clientePostDTO) {
        return this.clienteMapper.toDTO(this.clienteRepository.save(this.clienteMapper.toModel(clientePostDTO)));
    }

    public Page<ClienteDTO> getAll(Pageable pageable) {
        return this.clienteRepository.findAll(pageable).map(this.clienteMapper::toDTO);
    }

    public ClienteDTO saque(String numConta, BigDecimal valor) {
        Cliente c = this.clienteRepository.findByNumeroConta(numConta);
        if(valor.compareTo(new BigDecimal(0))<=0) return this.clienteMapper.toDTO(c);
        if(valor.compareTo(new BigDecimal(100))>0 && valor.compareTo(new BigDecimal(300))<=0 && !c.getPlanoExclusive())
            valor = valor.add(valor.multiply(BigDecimal.valueOf(0.4)).divide(BigDecimal.valueOf(100)));
        else if(valor.compareTo(new BigDecimal(300))>0 && !c.getPlanoExclusive())
            valor = valor.add(valor.multiply(BigDecimal.valueOf(1)).divide(BigDecimal.valueOf(100)));
        c.setSaldo(c.getSaldo().subtract(valor));
        c.getTransacoes().add(new Transacao(0L, c.getId(), TipoTransacao.SAQUE, valor, new Date(), c));
        return this.clienteMapper.toDTO(this.clienteRepository.save(c));
    }

    public ClienteDTO deposito(String numConta, BigDecimal valor) {
        Cliente c = this.clienteRepository.findByNumeroConta(numConta);
        if(valor.compareTo(new BigDecimal(0))<=0) return this.clienteMapper.toDTO(c);
        c.setSaldo(c.getSaldo().add(valor));
        c.getTransacoes().add(new Transacao(0L, c.getId(), TipoTransacao.DEPOSITO, valor, new Date(), c));
        return this.clienteMapper.toDTO(this.clienteRepository.save(c));
    }
}
