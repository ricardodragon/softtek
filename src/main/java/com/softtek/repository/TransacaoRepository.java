package com.softtek.repository;

import com.softtek.model.Transacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

public interface TransacaoRepository extends PagingAndSortingRepository<Transacao, Long> {

    Page<Transacao> findByDataBetween(Date data, Date endDate, Pageable page);
}
