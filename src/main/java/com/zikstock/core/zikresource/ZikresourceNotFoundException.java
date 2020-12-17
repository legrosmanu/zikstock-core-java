package com.zikstock.core.zikresource;

import com.zikstock.core.ZikstockCoreError;

public class ZikresourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private ZikstockCoreError error;

    ZikresourceNotFoundException(String id) {
        super("Could not find Zikresource " + id);
        this.error = new ZikstockCoreError("404-1", this.getMessage());
    }

    public ZikstockCoreError getError() {
        return this.error;
    }
    
}
