package com.linkapital.core.services.bank_operation.contract;

import com.linkapital.core.services.bank_operation.contract.to.BankOperationTO;
import com.linkapital.core.services.bank_operation.datasource.domain.BankOperation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-14T18:35:17-0300",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.1.jar, environment: Java 16.0.2 (Amazon.com Inc.)"
)
public class BankOperationBinderImpl implements BankOperationBinder {

    @Override
    public List<BankOperationTO> bind(Collection<BankOperation> source) {
        if ( source == null ) {
            return null;
        }

        List<BankOperationTO> list = new ArrayList<BankOperationTO>( source.size() );
        for ( BankOperation bankOperation : source ) {
            list.add( bankOperationToBankOperationTO( bankOperation ) );
        }

        return list;
    }

    protected BankOperationTO bankOperationToBankOperationTO(BankOperation bankOperation) {
        if ( bankOperation == null ) {
            return null;
        }

        BankOperationTO bankOperationTO = new BankOperationTO();

        if ( bankOperation.getId() != null ) {
            bankOperationTO.setId( bankOperation.getId() );
        }
        bankOperationTO.setUf( bankOperation.getUf() );
        bankOperationTO.setProduct( bankOperation.getProduct() );
        bankOperationTO.setType( bankOperation.getType() );
        bankOperationTO.setFinancialCost( bankOperation.getFinancialCost() );
        bankOperationTO.setFinancialAgent( bankOperation.getFinancialAgent() );
        bankOperationTO.setGracePeriod( bankOperation.getGracePeriod() );
        bankOperationTO.setAmortizationPeriod( bankOperation.getAmortizationPeriod() );
        bankOperationTO.setTax( bankOperation.getTax() );
        bankOperationTO.setContractedValue( bankOperation.getContractedValue() );
        bankOperationTO.setContractedDate( bankOperation.getContractedDate() );
        bankOperationTO.setModified( bankOperation.getModified() );
        bankOperationTO.setStatus( bankOperation.getStatus() );

        return bankOperationTO;
    }
}
