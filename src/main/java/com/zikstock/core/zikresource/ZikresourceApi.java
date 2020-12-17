package com.zikstock.core.zikresource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import com.zikstock.core.ZikstockCoreError;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class ZikresourceApi {

    @Autowired
    private ZikresourceRepository zikresourceRepository;


    /*****
     * Exceptions handlers
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ZikstockCoreError handleValidationExceptions(MethodArgumentNotValidException ex) {
        ZikstockCoreError error = new ZikstockCoreError("400-1", ex.getMessage());
        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ZikresourceNotFoundException.class)
    public ZikstockCoreError handleZikResourceNotFound(ZikresourceNotFoundException ex) {
        return ex.getError();
    }

    /*****
     * Endpoints
     */
    @PostMapping("/zikresources")
    public Zikresource createZikresource(@Valid @RequestBody Zikresource zikresource) {
        return zikresourceRepository.save(zikresource);
    }

    @PutMapping("/zikresources/{id}")
    public Zikresource updateZikResource(@PathVariable String id, @Valid @RequestBody Zikresource zikresource){
        Zikresource zikresourceUpdated = null;
        Optional<Zikresource> opt = zikresourceRepository.findById(id);
        zikresourceUpdated = opt.orElseThrow(() -> new ZikresourceNotFoundException(id));
        return zikresourceUpdated;
    }

    @GetMapping("/zikresources/{id}")
    public Zikresource getZikResource(@PathVariable String id) {
        return zikresourceRepository.findById(id).orElseThrow(() -> new ZikresourceNotFoundException(id));
    }

    @GetMapping("/zikresources")
    public List<Zikresource> getZikresources() {
        return zikresourceRepository.findAll();
    }

    @DeleteMapping("/zikresources/{id}")
    public void deleteZikResource(@PathVariable String id) {
        zikresourceRepository.deleteById(id);
    }

}
