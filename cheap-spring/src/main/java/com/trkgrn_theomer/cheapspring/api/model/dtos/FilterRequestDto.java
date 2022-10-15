package com.trkgrn_theomer.cheapspring.api.model.dtos;

import lombok.Data;

import java.util.List;

@Data
public class FilterRequestDto {
    private List<String> brandName;
    private List<String> cpu;
    private List<String> gpu;
    private List<String> hdd;
    private List<String> ram;
    private List<String> operatingSystem;
    private List<String> screenSize;
    private List<String> color;
}
