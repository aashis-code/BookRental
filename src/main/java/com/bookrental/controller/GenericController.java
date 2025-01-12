package com.bookrental.controller;

import com.bookrental.dto.ListOfIdDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.service.GenericService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
public class GenericController<T, ID> extends BaseController  {

    private final GenericService<T, ID> genericService;

    public GenericController(GenericService<T, ID> genericService) {
        this.genericService = genericService;
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(getSuccessResponse("Successfully created entity.",this.genericService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable ID id) {
        return ResponseEntity.status(HttpStatus.OK).body(getSuccessResponse("Successfully fetched entity",this.genericService.findByPK(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> create(@Valid @RequestBody T t) {
        this.genericService.saveEntity(t);
        return ResponseEntity.status(HttpStatus.CREATED).body(getSuccessResponse("Successfully saved entity.",null));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable ID id) {
        this.genericService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(getSuccessResponse("Successfully deleted entity.",null));
    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseObject> deleteItemsByIds(@RequestBody ListOfIdDto<ID> ids) {
        this.genericService.deleteAllById(ids.getIds());
        return ResponseEntity.status(HttpStatus.OK).body(getSuccessResponse("Successfully deleted entities.",null));
    }

    @PostMapping("/undo-delete/{id}")
    public ResponseEntity<ResponseObject> unDeleteItemsById(@PathVariable ID id) {
        this.genericService.activeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(getSuccessResponse("Successfully restored entity.",null));
    }

    @PostMapping("/undo-delete")
    public ResponseEntity<ResponseObject> unDeleteItemsByIds(@RequestBody ListOfIdDto<ID> ids) {
        this.genericService.activeAll(ids.getIds());
        return ResponseEntity.status(HttpStatus.OK).body(getSuccessResponse("Successfully restored entities.",null));
    }

}
