package com.softtek.controller;

import com.softtek.dto.ClienteDTO;
import com.softtek.dto.ClientePostDTO;
import com.softtek.dto.TransacaoDTO;
import com.softtek.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Api(value = "Endpoints to manage clientes")
@Slf4j
@Validated
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    @ApiOperation(value = "List all available clients", response = ClienteDTO.class)
    public ResponseEntity<Page<ClienteDTO>> getAllClientes(@NotNull final Pageable pageable){
        return new ResponseEntity<>(this.clienteService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/transacoes")
    @ApiOperation(value = "List all available clients", response = TransacaoDTO.class)
    public ResponseEntity<Page<TransacaoDTO>> getTransacoesByData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data, @NotNull final Pageable pageable){
        return new ResponseEntity<>(this.clienteService.getTransacaoByData(pageable, data), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Post cliente", response = ClientePostDTO.class)
    public ResponseEntity<ClienteDTO> postClientes(@RequestBody ClientePostDTO clientePostDTO){
        return new ResponseEntity<>(this.clienteService.post(clientePostDTO), HttpStatus.OK);
    }

    @PutMapping("/saque/{numConta}")
    @ApiOperation(value = "Post cliente", response = ClientePostDTO.class)
    public ResponseEntity<ClienteDTO> saque(@PathVariable String numConta, @NotNull @RequestParam BigDecimal valor){
        try {
            return new ResponseEntity<>(this.clienteService.saque(numConta, valor), HttpStatus.OK);
        }catch(EntityNotFoundException e){
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/deposito/{numConta}")
    @ApiOperation(value = "Post cliente", response = ClientePostDTO.class)
    public ResponseEntity<ClienteDTO> deposito(@PathVariable String numConta, @NotNull @RequestParam BigDecimal valor){
        try {
            return new ResponseEntity<>(this.clienteService.deposito(numConta, valor), HttpStatus.OK);
        }catch(EntityNotFoundException e){
            return new ResponseEntity("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
    }

}
