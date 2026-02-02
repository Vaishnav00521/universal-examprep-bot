package com.exambot.universalbot.controller;

import com.exambot.universalbot.service.ExamService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/exam")
public class ExamController {  // <--- The class starts here

    private final ExamService examService;

    // Constructor Injection
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    // Endpoint 1: Upload
    @PostMapping("/upload")
    public Map<String, String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String examId = examService.uploadExamPdf(file);
        return Map.of("examId", examId, "message", "Upload success! Use this ID to chat.");
    }

    // Endpoint 2: Chat
    @GetMapping("/chat")
    public Map<String, String> chat(@RequestParam String query, @RequestParam String examId) {
        String answer = examService.chatWithExam(query, examId);
        return Map.of("answer", answer);
    }

} // <--- The class MUST end here. Do not add code after this line.