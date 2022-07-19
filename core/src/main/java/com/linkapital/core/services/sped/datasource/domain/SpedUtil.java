package com.linkapital.core.services.sped.datasource.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SpedUtil {

    private LocalDate initDate;
    private LocalDate endDate;
    private List<String[]> balances;
    private List<String[]> demonstrations;

    public SpedUtil() {
        this.balances = new ArrayList<>();
        this.demonstrations = new ArrayList<>();
    }

    public SpedUtil withInitDate(LocalDate initDate) {
        setInitDate(initDate);
        return this;
    }

    public SpedUtil withEndDate(LocalDate endDate) {
        setEndDate(endDate);
        return this;
    }

}
