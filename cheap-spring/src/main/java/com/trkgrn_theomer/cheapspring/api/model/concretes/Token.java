package com.trkgrn_theomer.cheapspring.api.model.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token implements Serializable {
    private String username;
    private String jwt;

}
