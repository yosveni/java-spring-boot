package com.linkapital.core.util;


import static com.linkapital.core.services.invoice.datasource.domain.CfopData.IN_ADD_PURCHASES;
import static com.linkapital.core.services.invoice.datasource.domain.CfopData.IN_ADD_RETURNS_CANCELLATIONS;
import static com.linkapital.core.services.invoice.datasource.domain.CfopData.IN_ADD_TRANSFERS;

/**
 * Como queda el analisis de los ingresos y gastos, cuales suman o restan en dependeincia si la factura es Emitida o recibida
 * <p>
 * En las facturas emitidas
 * Suman en los INGRESOS
 * - Las Emitidas que no sean devoluciones, cancelaciones, compras
 * - Las Recibidas que sean sean devoluciones, cancelaciones
 * En las facturas recibidas
 * Suman en los gastos
 * - Las recibidas que no sean devoluciones, cancelaciones
 * - Las emitidas que son devoluciones, cancelaciones, compras
 * Además las facturas de transferencias no cuentas en ninguna, ni en los gatos ni en los ingresos
 */

public class QueryUtil {

    public static final String FILTER_DATA = "select case " +
            " when s1.month = 1  then 'Jan' " +
            " when s1.month = 2  then 'Fev' " +
            " when s1.month = 3  then 'Mar' " +
            " when s1.month = 4  then 'Abr' " +
            " when s1.month = 5  then 'Mai' " +
            " when s1.month = 6  then 'Jun' " +
            " when s1.month = 7  then 'Jul' " +
            " when s1.month = 8  then 'Ago' " +
            " when s1.month = 9  then 'Set' " +
            " when s1.month = 10 then 'Out' " +
            " when s1.month = 11 then 'Nov' " +
            " when s1.month = 12 then 'Dez' " +
            " else 'Undefined' end as month," +
            " cast(s1.month as integer) as month_number," +
            " s1.year ";

    public static final String QUERY_HORIZONTAL_BILLING = FILTER_DATA +
            ", s1.total from (select cast(extract(year from i.issuance_date) as int) as year, " +
            " extract (month from i.issuance_date) as month, sum(i.total) as total " +
            " from linkapital.tab_invoice i " +
            " where i.company_invoice_id = :param " +
            " and ( (i.type = 0 and i.status NOT IN (" + IN_ADD_RETURNS_CANCELLATIONS + ", " + IN_ADD_PURCHASES + ", " + IN_ADD_TRANSFERS + " )) or (i.type = 1 and i.status IN (" + IN_ADD_RETURNS_CANCELLATIONS + ")) )" +
            " group by 1,2 order by 1,2) s1";

    public static final String QUERY_HORIZONTAL_PAYMENT = FILTER_DATA +
            ", s1.total from (select cast(extract(year from i.issuance_date) as int) as year, " +
            " extract (month from i.issuance_date) as month, sum(i.total) as total " +
            " from linkapital.tab_invoice i " +
            " where i.company_received_id = :param " +
            " and ( (i.type = 1 and i.status NOT IN (" + IN_ADD_RETURNS_CANCELLATIONS + ", " + IN_ADD_TRANSFERS + " )) or (i.type = 0 and i.status IN (" + IN_ADD_RETURNS_CANCELLATIONS + ", " + IN_ADD_PURCHASES + ")) )" +
            " group by 1,2 order by 1,2) s1";

    public static final String QUERY_VERTICAL_BILLING = "select tsr.cnpj as clientCNPJ," +
            " tsr.name as clientName," +
            " sum(i.total) as total" +
            " from linkapital.tab_invoice i" +
            " inner join linkapital.tab_sender_recipient tsr on tsr.id = i.receipt_id" +
            " where i.company_invoice_id = :param " +
            " and ( (i.type = 0 and i.status NOT IN (" + IN_ADD_RETURNS_CANCELLATIONS + ", " + IN_ADD_PURCHASES + ", " + IN_ADD_TRANSFERS + "  )) or (i.type = 1 and i.status IN (" + IN_ADD_RETURNS_CANCELLATIONS + ")) )" +
            " group by 1, 2 order by 1, 2 ";

    public static final String QUERY_VERTICAL_PAYMENT = "select tsr.cnpj as clientCNPJ," +
            " tsr.name as clientName," +
            " sum(i.total) as total" +
            " from linkapital.tab_invoice i" +
            " inner join linkapital.tab_sender_recipient tsr on tsr.id = i.sender_id" +
            " where i.company_received_id = :param " +
            " and ( (i.type = 1 and i.status NOT IN (" + IN_ADD_RETURNS_CANCELLATIONS + ", " + IN_ADD_TRANSFERS + " )) or (i.type = 0 and i.status IN (" + IN_ADD_RETURNS_CANCELLATIONS + ", " + IN_ADD_PURCHASES + ")) )" +
            " group by 1, 2 order by 1, 2";

    public static final String QUERY_INVOICE_TAX = FILTER_DATA +
            ", s1.ipi, s1.pis, s1.cofins " +
            " from (select cast(extract(year from i.issuance_date) as int) as year," +
            " extract (month from i.issuance_date) as month, sum(tx.ipi) as ipi, sum(tx.pis) as pis," +
            " sum(tx.cofins) as cofins from linkapital.tab_invoice i inner join linkapital.tab_indirect_tax tx " +
            " on i.indirect_tax_id = tx.id where i.company_invoice_id = :param group by 1,2 order by 1,2) s1";

    private QueryUtil() {
    }

}
