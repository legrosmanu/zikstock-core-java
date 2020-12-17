package com.zikstock.core.zikresource;

import javax.validation.constraints.Size;

import com.mongodb.lang.NonNull;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ZikresourceTag {

    @NonNull
    private final String label;
    
    @NonNull @Size(max=255)
    private final String value;
    
}
