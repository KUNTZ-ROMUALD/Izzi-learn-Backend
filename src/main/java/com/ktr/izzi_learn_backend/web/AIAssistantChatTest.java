package com.ktr.izzi_learn_backend.web;

import com.ktr.izzi_learn_backend.agents.TransactionAgent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@CrossOrigin("*")
public class AIAssistantChatTest {
   private TransactionAgent transactionAgent;

    public AIAssistantChatTest(TransactionAgent transactionAgent) {
        this.transactionAgent = transactionAgent;
    }

    @GetMapping("/AskAgent")
    public Flux<String> chat (@RequestParam( defaultValue = "bonjour") String message){



        return transactionAgent.chat(message);
    }
}
