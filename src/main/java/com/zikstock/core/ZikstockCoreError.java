package com.zikstock.core;

import lombok.Data;
import lombok.NonNull;

@Data
public class ZikstockCoreError {

    @NonNull
    private String code;
    @NonNull
    private String message;
    
}
