package io.github.dougllasfps.clientes.rest;

import io.github.dougllasfps.clientes.model.entity.Cliente;
import io.github.dougllasfps.clientes.model.entity.ServicoPrestado;
import io.github.dougllasfps.clientes.model.reporitory.ClienteRepository;
import io.github.dougllasfps.clientes.model.reporitory.ServicoPrestadoRepository;
import io.github.dougllasfps.clientes.rest.dto.ServicoPrestadoDTO;
import io.github.dougllasfps.clientes.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/servicos-prestados")
@RequiredArgsConstructor
public class ServicoPrestadoController {

    private final ClienteRepository clienteRepository;
    private final ServicoPrestadoRepository servicoPrestadoRepository;
    private final BigDecimalConverter bigDecimalConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoPrestado salvar(@RequestBody @Valid ServicoPrestadoDTO dto){
        LocalDate data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Integer idCliente = dto.getIdCliente();

        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente Inexistente"));

    ServicoPrestado servicoPrestado = new ServicoPrestado();
    servicoPrestado.setDescricao(dto.getDescricao());
    servicoPrestado.setData(data);
    servicoPrestado.setCliente(cliente);
    servicoPrestado.setValor(bigDecimalConverter.converter(dto.getPreco()));

    return servicoPrestadoRepository.save(servicoPrestado);
    }

    @GetMapping
    public List<ServicoPrestado> pesquisar(
            @RequestParam(value = "nome", required = false, defaultValue = "") String nome,
            @RequestParam(value = "mes", required = false) String mes
            ){
        return servicoPrestadoRepository.findByNomeClienteAndMes("%"+nome+"%", mes);
    }
}
