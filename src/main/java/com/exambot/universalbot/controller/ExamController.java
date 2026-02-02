package com.exambot.universalbot.controller;

import com.exambot.universalbot.service.ExamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@RequestMapping("/api/exam")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            String examId = examService.uploadExamPdf(file);
            return ResponseEntity.ok(Map.of("examId", examId, "message", "Upload Success"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/chat")
    public ResponseEntity<?> chat(@RequestParam String query, @RequestParam String examId) {
        try {
            String answer = examService.chatWithExam(query, examId);
            return ResponseEntity.ok(Map.of("answer", answer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("answer", "Error: " + e.getMessage()));
        }
    }
}