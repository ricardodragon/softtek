package com.softtek.controller;

import com.softtek.dto.ClienteDTO;
import com.softtek.dto.ClientePostDTO;
import com.softtek.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Api(value = "Endpoints to manage clientes")
@Slf4j
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @ApiOperation(value = "List all available produtos", response = ClientePostDTO.class)
    public ResponseEntity<ClienteDTO> postProdutos(@RequestBody ClientePostDTO clientePostDTO){
        try {
            return new ResponseEntity<>(this.clienteService.post(clientePostDTO), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
