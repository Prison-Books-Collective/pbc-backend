package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
public class ResourceController {

    @Autowired
    ResourceRepository resourceRepository;

    public ResourceController(@Autowired ResourceRepository resourceRepository){
        this.resourceRepository = resourceRepository;
    }

    @PostMapping(path="/addResource")
    public ResponseEntity<Resource> addNewResource(@RequestBody Resource resource){
        Resource save = resourceRepository.save(resource);
        return ResponseEntity.ok(save);
    }

    @PutMapping(path="/updateResource")
    public ResponseEntity<Resource> updateResource(@RequestBody Resource resource) {
        Optional<Resource> optional = resourceRepository.findById(resource.getId());
        if (optional.isPresent()) {
            Resource actual = optional.get();
            actual.setTitle(resource.getTitle());
            actual.setAuthors(resource.getAuthors());
            Resource save = resourceRepository.save(actual);
            return ResponseEntity.ok(save);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
