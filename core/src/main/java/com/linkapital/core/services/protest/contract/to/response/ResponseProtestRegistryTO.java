package com.linkapital.core.services.protest.contract.to.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseProtestRegistryTO {

    private String nome;
    private String telefone;
    private String endereco;
    private String cidade_codigo;
    private String cidade_codigo_ibge;
    private String municipio;
    private String cidade;
    private String bairro;
    private String atualizacao_data;
    private String periodo_pesquisa;
    private int codigo;
    private int quantidade;
    private List<ResponseProtestTO> protestos;

    public ResponseProtestRegistryTO() {
        this.protestos = new ArrayList<>();
    }

}
