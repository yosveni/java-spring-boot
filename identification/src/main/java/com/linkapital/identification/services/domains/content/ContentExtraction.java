package com.linkapital.identification.services.domains.content;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContentExtraction {

    private String type;
    private String stdType;
    private String processType;
    private int pageNumber;
    private double score;
    private byte[] image;
    private List<String> tags;
    private List<ScoreData> fields;
    private List<Object> tables;

}
