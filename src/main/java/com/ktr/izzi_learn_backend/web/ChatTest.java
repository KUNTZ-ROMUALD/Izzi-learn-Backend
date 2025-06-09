package com.ktr.izzi_learn_backend.web;

import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class ChatTest {
    private ChatLanguageModel chatLanguageModel;
    public ChatTest(ChatLanguageModel chatLanguageModel){
        this.chatLanguageModel= chatLanguageModel;
    }
    @GetMapping("/chat")
    public String chat (@RequestParam( defaultValue = "bonjour") String message){
        return chatLanguageModel.chat(message);
    }
}
