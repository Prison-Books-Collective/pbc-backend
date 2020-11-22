package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Book;
import com.prisonbooks.PrisonBooksCollective.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ResourceController {

    @Autowired
    ResourceRepository resourceRepository;

    public ResourceController(@Autowired ResourceRepository resourceRepository){
        this.resourceRepository = resourceRepository;
    }

    @PostMapping(path="/addResource")
    public ResponseEntity<Resource> addNewBook(@RequestBody Resource resource){
        Resource save = resourceRepository.save(resource);
        return ResponseEntity.ok(save);

    }

}
