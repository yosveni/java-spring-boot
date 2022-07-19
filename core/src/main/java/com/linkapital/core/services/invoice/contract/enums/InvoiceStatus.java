package com.linkapital.core.services.invoice.contract.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InvoiceStatus {
    UNKNOWN(0),                //0 Desconocida
    PURCHASE(1),               //1 Compra
    ISSUED(2),                 //2 Emitida
    CANCELLED(3),              //3 Anulacion
    BACK(4),                   //4 Devuelta,
    INDUSTRIALIZATION(5),      //5 Industrialização
    TRANSFERS(6),              //6 Transferencias
    ACQUISITION(7),            //7 Adquisiciones
    ENTRY(8),                  //8 Entrada
    RETURN(9),                 //9 Retorno
    INTEGRATION_SYSTEMS(10),   //10 Sitemas de Integracion
    VENDA(11),                 //11 Vendas,
    SERVICE_PROVISION(12),     //12 Prestacion de servicio
    REMIITTANCES(13),          //13 Remesas
    LAUNCHING(14);             //14 Lanzamiento

    private final int value;
}
