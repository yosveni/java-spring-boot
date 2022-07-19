package com.linkapital.core.services.shared.impl.person;

import com.linkapital.core.services.person.PersonService;
import com.linkapital.core.services.person.datasource.domain.Person;
import com.linkapital.core.services.shared.PersonConverter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static com.linkapital.core.services.shared.contract.BacenBinder.BACEN_BINDER;
import static com.linkapital.core.services.shared.contract.HistoricalCriminalBinder.HISTORICAL_CRIMINAL_BINDER;
import static com.linkapital.core.services.shared.contract.HistoricalFunctionalBinder.HISTORICAL_FUNCTIONAL_BINDER;
import static com.linkapital.core.services.shared.contract.IrpfBinder.IRPF_BINDER;
import static com.linkapital.core.services.shared.contract.JudicialProcessBinder.JUDICIAL_PROCESS_BINDER;

@Service
public class PersonLegalConverterImpl implements PersonConverter {

    //Level 1
    private static final String irpfRestituicao = "irpfRestituicao";
    private static final String historicalFunctional = "historicoFuncional";
    private static final String judicialProcess = "processoJudicialTotalizadores";
    private static final String historicalCriminal = "historicoCriminal";
    private static final String centralBank = "bancoCentral";
    //Level 2
    private static final String irpf = "exercicios";
    private static final String disabilities = "inabilitados";

    private final PersonService personService;

    public PersonLegalConverterImpl(PersonService personService) {
        this.personService = personService;
    }

    @PostConstruct
    public void init() {
        personService.addConverter(this);
    }

    @Override
    public Person convert(Person person, Map data) throws ParseException {
        var irpfLevel2 = data.get(irpfRestituicao) == null
                ? null
                : (List<Map>) ((Map) data.get(irpfRestituicao)).get(irpf);
        if (irpfLevel2 != null) {
            person.getIrpf().clear();
            person.getIrpf().addAll(IRPF_BINDER.bind(irpfLevel2));
        }

        var historicalFunctionalLevel2 = data.get(historicalFunctional) == null
                ? null
                : (List<Map>) data.get(historicalFunctional);
        if (historicalFunctionalLevel2 != null) {
            person.getHistoricalFunctional().clear();
            person.getHistoricalFunctional().addAll(HISTORICAL_FUNCTIONAL_BINDER.bind(historicalFunctionalLevel2));
        }

        if (data.get(judicialProcess) != null)
            person.setJudicialProcess(JUDICIAL_PROCESS_BINDER.bind((Map) data.get(judicialProcess)));

        if (data.get(historicalCriminal) != null)
            person.setHistoricalCriminal(HISTORICAL_CRIMINAL_BINDER.bind((Map) data.get(historicalCriminal)));

        var disabilitiesLevel2 = data.get(centralBank) == null
                ? null
                : (List<Map>) ((Map) data.get(centralBank)).get(disabilities);
        if (disabilitiesLevel2 != null) {
            person.getDisabilitiesBacens().clear();
            person.getDisabilitiesBacens().addAll(BACEN_BINDER.bind(disabilitiesLevel2));
        }

        return person;
    }

}
