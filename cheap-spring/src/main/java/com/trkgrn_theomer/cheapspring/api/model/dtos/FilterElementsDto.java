package com.trkgrn_theomer.cheapspring.api.model.dtos;

import lombok.Data;

import java.util.List;

@Data
public class FilterElementsDto {
    private List<String> brandNames;
    private List<String> CPUs;
    private List<String> GPUs;
    private List<String> HDDs;
    private List<String> operatingSystems;
    private List<String> screenSizes;
    private List<String> colors;
}
