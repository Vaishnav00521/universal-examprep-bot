package com.exambot.universalbot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public ExamService(VectorStore vectorStore, ChatClient.Builder chatClientBuilder) {
        this.vectorStore = vectorStore;
        // The "NLP" Brain: Instructions for Multilingual Support
        this.chatClient = chatClientBuilder
                .defaultSystem("""
                    You are an advanced AI Tutor with Multilingual NLP capabilities.
                    
                    INSTRUCTIONS:
                    1. DETECT LANGUAGE: Analyze the user's question language (Hindi, English, Hinglish).
                    2. MATCH OUTPUT: Reply in the EXACT same language/style.
                       - User: "Iska matlab kya hai?" -> You: "Iska matlab hai..."
                       - User: "Summarize this." -> You: "Here is the summary..."
                    3. SOURCE TRUTH: Answer ONLY based on the provided CONTEXT.
                    """)
                .build();
    }

    public String uploadExamPdf(MultipartFile file) throws IOException {
        PagePdfDocumentReader reader = new PagePdfDocumentReader(file.getResource());
        List<Document> documents = reader.get();
        TokenTextSplitter splitter = new TokenTextSplitter(500, 100, 5, 10000, true);
        List<Document> splitDocuments = splitter.apply(documents);

        String examId = UUID.randomUUID().toString();
        for (Document doc : splitDocuments) {
            doc.getMetadata().put("examId", examId);
        }
        vectorStore.add(splitDocuments);
        return examId;
    }

    public String chatWithExam(String query, String examId) {
        try {
            // 1. Search Vector DB
            List<Document> similarDocuments = vectorStore.similaritySearch(
                    SearchRequest.builder()
                            .query(query)
                            .topK(3)
                            .filterExpression("examId == '" + examId + "'")
                            .build()
            );

            // 2. Prepare Context
            String context = similarDocuments.stream()
                    .map(Document::getText)
                    .collect(Collectors.joining("\n"));

            // 3. Generate Answer (with Error Handling)
            return chatClient.prompt()
                    .user(u -> u.text("""
                        CONTEXT:
                        {context}
                        
                        QUESTION:
                        {query}
                        """)
                            .param("context", context)
                            .param("query", query)
                    )
                    .call()
                    .content();

        } catch (Exception e) {
            // FIX: If AI crashes, return the error to the chat window
            return "❌ **AI Error:** " + e.getMessage();
        }
    }
}