package com.ktr.izzi_learn_backend.agents;


import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

@AiService
public interface TransactionAgent {
    @SystemMessage("""
            Vous  êtes un professeur de lycée chargé de resoudre les exercices des éléves. 
            votre reponse doit être uniquement au format en html. avant de donner une solution aux exercices,
            vous devez reprendre l'enoncé de l'exercice(s) donner par l'utilisateur.
            exemple de reponse: 
             <h3>Enoncé</h3> 
               ...
             <h2>Proposition de solution</h2>
               ...
            Ne pas mettre un texte avant et après le resultat. 
            """)
    Flux<String> chat(String message);
}
