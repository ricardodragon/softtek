package com.softtek.controller;

import com.softtek.dto.ClienteDTO;
import com.softtek.dto.ClientePostDTO;
import com.softtek.model.Cliente;
import com.softtek.service.ClienteService;
import com.sun.istack.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Api(value = "Endpoints to manage clientes")
@Slf4j
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    @ApiOperation(value = "List all available clients", response = ClienteDTO.class)
    public ResponseEntity<Page<ClienteDTO>> getAllClientes(@NotNull final Pageable pageable){
        try {
            return new ResponseEntity<>(this.clienteService.getAll(pageable), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @ApiOperation(value = "Post cliente", response = ClientePostDTO.class)
    public ResponseEntity<ClienteDTO> postClientes(@RequestBody ClientePostDTO clientePostDTO){
        try {
            return new ResponseEntity<>(this.clienteService.post(clientePostDTO), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/saque/{numConta}")
    @ApiOperation(value = "Post cliente", response = ClientePostDTO.class)
    public ResponseEntity<ClienteDTO> saque(@PathVariable String numConta, @RequestParam BigDecimal valor){
        try {
            return new ResponseEntity<>(this.clienteService.saque(numConta, valor), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/deposito/{numConta}")
    @ApiOperation(value = "Post cliente", response = ClientePostDTO.class)
    public ResponseEntity<ClienteDTO> deposito(@PathVariable String numConta, @RequestParam BigDecimal valor){
        try {
            return new ResponseEntity<>(this.clienteService.deposito(numConta, valor), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
