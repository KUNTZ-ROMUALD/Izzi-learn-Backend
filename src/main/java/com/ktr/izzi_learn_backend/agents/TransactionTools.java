package com.ktr.izzi_learn_backend.agents;

import com.ktr.izzi_learn_backend.entities.Epreuve;
import com.ktr.izzi_learn_backend.repositories.EpreuveRepository;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class TransactionTools {
    EpreuveRepository epreuveRepository;
    public TransactionTools( EpreuveRepository epreuveRepository){
        this.epreuveRepository=epreuveRepository;
    }

    @Tool("Get all epreuve")
    public List<Epreuve> getAllEpreuve(){
        return epreuveRepository.findAll();
    }

}
