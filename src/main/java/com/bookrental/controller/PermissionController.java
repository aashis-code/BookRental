package com.bookrental.controller;

import com.bookrental.model.Permission;
import com.bookrental.service.GenericService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/permission")
public class PermissionController extends GenericController<Permission, Integer>{

    public PermissionController(GenericService<Permission, Integer> genericService) {
        super(genericService);
    }

}
