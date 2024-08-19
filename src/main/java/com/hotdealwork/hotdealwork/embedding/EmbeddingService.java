package com.hotdealwork.hotdealwork.embedding;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.embedding.EmbeddingRequest;
import com.theokanning.openai.embedding.EmbeddingResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbeddingService {

    private final OpenAiService openAiService;

    public EmbeddingService(@Value("${openai.api-key}") String apiKey) {
        this.openAiService = new OpenAiService(apiKey);
    }

    public List<Double> getEmbedding(String text) {
        List<String> texts = List.of(text);

        EmbeddingRequest embeddingRequest = EmbeddingRequest.builder()
                .model("text-embedding-3-small")
                .input(texts)
                .build();

        EmbeddingResult result = openAiService.createEmbeddings(embeddingRequest);
//        System.out.println(result.getData().get(0).getEmbedding());
        return result.getData().get(0).getEmbedding();
    }


}
