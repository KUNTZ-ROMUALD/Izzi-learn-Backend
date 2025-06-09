package com.ktr.izzi_learn_backend.web;


import com.ktr.izzi_learn_backend.agents.TransactionAgent;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.data.message.*;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.output.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin("*")
public class OCRByChat {

    private TransactionAgent transactionAgent;
    private ChatLanguageModel chatLanguageModel;
    public  OCRByChat(TransactionAgent transactionAgent){
        this.transactionAgent = transactionAgent;
        chatLanguageModel = OpenAiChatModel.builder()
                .modelName(OpenAiChatModelName.GPT_4_O)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    @PostMapping(value = "/imageToText" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  Flux<String>imageTotext(@RequestParam("file")MultipartFile file, @RequestParam( defaultValue = "Extrait le contenu fichier") String question) throws IOException {
        byte[] imageData= file.getBytes();
        String imageBase64= Base64.getEncoder().encodeToString(imageData);

        Image img = Image.builder().base64Data(imageBase64).mimeType("image/png").build();
        ImageContent imageContent= ImageContent.from(img);
        SystemMessage systemMessage= new SystemMessage("Extrait moi le contenu de l'image");
        UserMessage  userMessage=  new UserMessage(TextContent.from(question),imageContent);

        Response<AiMessage> response = chatLanguageModel.generate(List.of(systemMessage,userMessage));

        return transactionAgent.chat(response.content().toString());
    }
}
