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
        // Build the AI Client with a System Prompt
        this.chatClient = chatClientBuilder
                .defaultSystem("""
                    You are an intelligent, multilingual AI Study Assistant.
                    
                    YOUR RULES:
                    1. LANGUAGE: Always reply in the SAME language the user asks in.
                       - If they ask in Hindi, reply in Hindi.
                       - If they ask in Hinglish (e.g., "Azure VM kaise banaye?"), reply in Hinglish.
                       - If they ask in English, reply in English.
                    
                    2. CONTEXT: Use the provided 'CONTEXT' from the PDF to answer.
                       - If the answer is in the PDF, explain it clearly.
                       - If the answer is NOT in the PDF, say politely: "I couldn't find that in your document."
                    
                    3. TONE: Be helpful, encouraging, and professional.
                    """)
                .build();
    }

    public String uploadExamPdf(MultipartFile file) throws IOException {
        // 1. Read PDF
        PagePdfDocumentReader reader = new PagePdfDocumentReader(file.getResource());
        List<Document> documents = reader.get();

        // 2. Split into small chunks
        TokenTextSplitter splitter = new TokenTextSplitter(500, 100, 5, 10000, true);
        List<Document> splitDocuments = splitter.apply(documents);

        // 3. Generate ID & Tag Documents
        String examId = UUID.randomUUID().toString();
        for (Document doc : splitDocuments) {
            doc.getMetadata().put("examId", examId);
        }

        // 4. Save to Database
        vectorStore.add(splitDocuments);

        return examId;
    }

    public String chatWithExam(String query, String examId) {
        // 1. FIXED: Use the 'builder' pattern for SearchRequest
        List<Document> similarDocuments = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)            // Set the query
                        .topK(3)                 // Get top 3 results
                        .filterExpression("examId == '" + examId + "'") // Filter by ID
                        .build()                 // Build the request
        );

        // 2. FIXED: Use 'getText()' instead of 'getContent()'
        String context = similarDocuments.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n"));

        // 3. Send to AI
        return chatClient.prompt()
                .user(u -> u.text("""
                    CONTEXT FROM PDF:
                    {context}
                    
                    USER QUESTION:
                    {query}
                    """)
                        .param("context", context)
                        .param("query", query)
                )
                .call()
                .content();
    }
}