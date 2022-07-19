package com.linkapital.core.services.person.datasource;

import com.linkapital.core.services.person.datasource.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByCpf(String cpf);

    List<Person> findAllByCpfIn(List<String> cpfList);

}
