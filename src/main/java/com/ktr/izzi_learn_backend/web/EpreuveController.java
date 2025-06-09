package com.ktr.izzi_learn_backend.web;

import com.ktr.izzi_learn_backend.entities.Epreuve;
import com.ktr.izzi_learn_backend.repositories.EpreuveRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class EpreuveController {

    private EpreuveRepository epreuveRepository;
    public EpreuveController(EpreuveRepository epreuveRepository){
        this.epreuveRepository=epreuveRepository;
    }
    @PostMapping("/saveEpreuve")
    public Epreuve SaveEpreuve(@RequestBody Epreuve epreuve){
        epreuveRepository.save(epreuve);
        return epreuve;
    }
    @GetMapping("/getAllEpreuve")
    public List<Epreuve> getAllEpreuve(){
        return epreuveRepository.findAll();
    }
    @GetMapping("/getEpreuve/{id}")
    public Optional<Epreuve> getEpreuve(@PathVariable Long id){
        return epreuveRepository.findById(id);
    }


}
