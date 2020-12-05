package com.prisonbooks.PrisonBooksCollective.controller;

import com.prisonbooks.PrisonBooksCollective.model.Zine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class ZineController {

    @Autowired
    ZineRepository zineRepository;

    @Autowired
    public ZineController(@Autowired ZineRepository zineRepository){

        this.zineRepository = zineRepository;
    }

    @PostMapping(path="/addZine")
    public ResponseEntity<Zine> addNewBook(@RequestBody Zine zine){
        Zine save = zineRepository.save(zine);
        return ResponseEntity.ok(save);
    }

    @GetMapping(path="/getZines")
    public ResponseEntity<List<Zine>> getZines(){
        Iterable<Zine> all = zineRepository.findAll();
        List<Zine> zines =  new LinkedList<>();
        all.forEach(zines::add);
        return  ResponseEntity.ok(zines);
    }

    @GetMapping(path="/getZine")
    public ResponseEntity<Zine> getZineByThreeLetterCode(@RequestParam String threeLetterCode){
        Optional<Zine> zine = zineRepository.findByThreeLetterCode(threeLetterCode);
        if (zine.isPresent()){
            return  ResponseEntity.ok(zine.get());
        }else{
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        }
    }


    @PutMapping(path="/updateZine")
    public ResponseEntity<Zine> updateResource(@RequestBody Zine zine) {
        Optional<Zine> optional = zineRepository.findById(zine.getId());
        if (optional.isPresent()) {
            Zine actual = optional.get();
            actual.setTitle(zine.getTitle());
            actual.setThreeLetterCode(zine.getThreeLetterCode());
            Zine save = zineRepository.save(actual);
            return ResponseEntity.ok(save);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
