package org.romain.demo2.controller;


import org.romain.demo2.dao.LabelDao;
import org.romain.demo2.model.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class LabelController {


    //@Autowired //Fait le lien avec la dépendence LabelDao
    //protected LabelDao labelDao;

    protected LabelDao labelDao;

    @Autowired
    public LabelController(LabelDao labelDao) {
        this.labelDao = labelDao;
    }

    @GetMapping("/label/{id}")
    public ResponseEntity<Label> /* être plus precis sur le retour de la methode */ get(@PathVariable int id) {

        Optional<Label> labelOptional = labelDao.findById(id);

        if (labelOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(labelOptional.get(), HttpStatus.OK);

    }

    @GetMapping("/labels")
    public List<Label> glabell() {

        return labelDao.findAll();
    }

    @PostMapping("/label")
    public ResponseEntity<Label> save(@RequestBody Label label) {
        labelDao.save(label);

        return new ResponseEntity<>(label, HttpStatus.CREATED);


    }

    @DeleteMapping("/label/{id}")
    public ResponseEntity<Label> delete(@PathVariable int id) {

        Optional<Label> labelOptional = labelDao.findById(id);

        if (labelOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        labelDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/label/{id}") //mise à jour
    public ResponseEntity<Label> update(
            @PathVariable int id,
            @RequestBody Label label) {

        Optional<Label> labelOptional = labelDao.findById(id);

        if (labelOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        label.setId(id);

        labelDao.save(label);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
