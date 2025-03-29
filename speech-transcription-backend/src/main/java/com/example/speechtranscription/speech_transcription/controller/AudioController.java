package com.example.speechtranscription.speech_transcription.controller;

import com.example.speechtranscription.speech_transcription.model.Transcription;
import com.example.speechtranscription.speech_transcription.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audio")
public class AudioController {

    @Autowired
    private AudioService audioService;

    @PostMapping("/start")
    public ResponseEntity<String> startRecording() {
        return ResponseEntity.ok(audioService.startRecording());
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopRecording() {
        return ResponseEntity.ok(audioService.stopRecording());
    }

    @GetMapping("/transcribe")
    public ResponseEntity<String> transcribeAudio() {
        return ResponseEntity.ok(audioService.transcribeAudio());
    }

    @GetMapping("/transcriptions")
    public ResponseEntity<List<Transcription>> getAllTranscriptions() {
        return ResponseEntity.ok(audioService.getAllTranscriptions());
    }
}
