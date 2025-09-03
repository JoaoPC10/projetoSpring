package io.github.dougllasfps.clientes.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class ServicoPrestadoDTO {

    @NotEmpty(message = "{campo.descricao.obrigatorio}")
    private String descricao;
    @NotEmpty(message = "{campo.data.invalido}")
    private String data;
    @NotEmpty(message = "{campo.preco.obrigatorio}")
    private String preco;
    private Integer idCliente;
}
