package com.lvbok.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Param {

    private String paramKey;

    private Object paramValue;
}
