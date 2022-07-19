package com.linkapital.core.services.company.datasource.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TAB_EMPLOYEE_GROWTH")
@SequenceGenerator(name = "gen_employee_growth", sequenceName = "seq_employee_growth", allocationSize = 1)
public class EmployeeGrowth implements Comparable<EmployeeGrowth> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_employee_growth")
    private Long id;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int employeeGrowth;

    @Column(nullable = false)
    private double growth;

    @Override
    public int compareTo(@NonNull EmployeeGrowth employeeGrowth) {
        if (getYear() == 0 || employeeGrowth.getYear() == 0)
            return 0;

        var n1 = Integer.valueOf(getYear());
        var n2 = Integer.valueOf(employeeGrowth.getYear());

        return n1.compareTo(n2);
    }

    public EmployeeGrowth withYear(int year) {
        setYear(year);
        return this;
    }

    public EmployeeGrowth withEmployeeGrowth(int employeeGrowth) {
        setEmployeeGrowth(employeeGrowth);
        return this;
    }

    public EmployeeGrowth withGrowth(double growth) {
        setGrowth(growth);
        return this;
    }

}
