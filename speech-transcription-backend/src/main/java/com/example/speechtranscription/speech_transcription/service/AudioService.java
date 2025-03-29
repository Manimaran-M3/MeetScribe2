package com.example.speechtranscription.speech_transcription.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.speechtranscription.speech_transcription.model.Transcription;
import com.example.speechtranscription.speech_transcription.repository.TranscriptionRepository;

@Service
public class AudioService {

    @Value("${audio.file-path}")
    private String outputFile;

    @Value("${python.script-path}")
    private String pythonScript;

    @Autowired
    private TranscriptionRepository transcriptionRepository;

    private Process recordingProcess;

    public String startRecording() {
        try {
            ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-y", "-f", "dshow", "-i", "audio=Stereo Mix (Realtek(R) Audio)", outputFile);
            recordingProcess = pb.start();
            return "Recording started...";
        } catch (IOException e) {
            return "Error starting recording: " + e.getMessage();
        }
    }

    public String stopRecording() {
        if (recordingProcess != null) {
            recordingProcess.destroy();
            return "Recording stopped.";
        }
        return "No recording in progress.";
    }

    public String transcribeAudio() {
        System.out.println("ENTERED THE TRANSCRIBING PROCESS");
        try {
            ProcessBuilder pb = new ProcessBuilder("python", pythonScript, outputFile);
            Process process = pb.start();
            System.out.println("STARTED THE TRANSCRIBING PROCESS");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String transcription = reader.lines().reduce("", (acc, line) -> acc + line + "\n");

                process.waitFor();
                System.out.println("TRANSCRIBED AUDIO :"+transcription);
                // Save transcription to database
                Transcription t = new Transcription(transcription);
                transcriptionRepository.save(t);
                
                return transcription;
            }
        } catch (IOException | InterruptedException e) {
            return "Error transcribing audio: " + e.getMessage();
        }
    }

    public List<Transcription> getAllTranscriptions() {
        return transcriptionRepository.findAll();
    }
}
