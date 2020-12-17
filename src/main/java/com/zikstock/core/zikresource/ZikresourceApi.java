package com.zikstock.core.zikresource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class ZikresourceApi {

    @Autowired
    private ZikresourceRepository zikresourceRepository;

    @PostMapping("/zikresources")
    public Zikresource createZikresource(@Valid @RequestBody Zikresource zikresource) {
        return this.zikresourceRepository.save(zikresource);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.put("code", "400-1");
            errors.put("details", errorMessage);
        });
        return errors;
    }

}
