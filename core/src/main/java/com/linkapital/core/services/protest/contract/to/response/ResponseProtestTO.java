package com.linkapital.core.services.protest.contract.to.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseProtestTO {

    private String cpf_cnpj;
    private String data_protesto;
    private String data_vencimento;
    private String chave;
    private String nome_apresentante;
    private String nome_cedente;
    private String tem_anuencia;
    private Double valor;

}
