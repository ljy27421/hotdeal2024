package com.hotdealwork.hotdealwork.embedding;

import com.hotdealwork.hotdealwork.board.Board;
import com.hotdealwork.hotdealwork.board.BoardRepository;
import com.hotdealwork.hotdealwork.user.SiteUser;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.embedding.EmbeddingRequest;
import com.theokanning.openai.embedding.EmbeddingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmbeddingService {

    private final OpenAiService openAiService;

    @Autowired
    private BoardRepository boardRepository;

    public EmbeddingService(@Value("${openai.api.key}") String apiKey) {
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

    public double calculatingCosineSimilarity(List<Double> vectorA, List<Double> vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i  < 1536; i++){
            dotProduct += vectorA.get(i) * vectorB.get(i);
            normA += Math.pow(vectorA.get(i), 2);
            normB += Math.pow(vectorB.get(i), 2);
        }

        double denominator = Math.sqrt(normA) * Math.sqrt(normB);
        if (denominator == 0) {
            return 0.0;
        }

        return dotProduct / denominator;
    }

    public List<Board> recommandBoards(SiteUser siteUser){
        List<Board> userInterestBoards = new ArrayList<>();
        for (int i = 0; i < siteUser.getInterest().size(); i++){
            userInterestBoards.add(boardRepository.getById(siteUser.getInterest().get(i)));
        }

        List<Double> userVector = new ArrayList<>(Collections.nCopies(1536,0.0));
        for (int i = 0; i < 1536; i++){
            for (int j = 0; j < siteUser.getInterest().size(); j++){
                userVector.set(i, userVector.get(i)+userInterestBoards.get(j).getEmbeddingVector().get(i));
            }
            userVector.set(i, userVector.get(i)/siteUser.getInterest().size());
        }

        List<Board> allBoards = boardRepository.findAll();

        List<Board> validBoards = allBoards.stream()
                .filter(board -> board.getEndDate() != null &&  board.getEndDate().isAfter(LocalDate.now()))
                .filter(board -> !siteUser.getInterest().contains(board.getId()))
                .toList();

        List<Board> recommandedBoards = validBoards.stream()
                .filter(board -> calculatingCosineSimilarity(userVector, board.getEmbeddingVector()) >= 0.45)
                .sorted((board1, board2) -> {
//                    double similarity1 = calculatingCosineSimilarity(siteUser.getInterestVector(), board1.getEmbeddingVector());
//                    double similarity2 = calculatingCosineSimilarity(siteUser.getInterestVector(), board2.getEmbeddingVector());
                    double similarity1 = calculatingCosineSimilarity(userVector, board1.getEmbeddingVector());
                    double similarity2 = calculatingCosineSimilarity(userVector, board2.getEmbeddingVector());
                    return Double.compare(similarity2, similarity1);
                })
                .toList();

        return recommandedBoards;
    }


}
