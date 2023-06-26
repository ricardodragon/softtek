package com.softtek.service;

import com.softtek.dto.ClienteDTO;
import com.softtek.dto.ClientePostDTO;
import com.softtek.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteDTO post(ClientePostDTO clientePostDTO) {
        return null;//this.clienteRepository.save(clientePostDTO);
    }
}
