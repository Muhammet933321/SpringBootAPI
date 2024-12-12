package com.Blix.UnityAPI.webSocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameWebSocketHandler extends TextWebSocketHandler {


    private Timer timer;
    private final Lock lock = new ReentrantLock();
    private float characterSpeed = 0.5f; // Karakter Hızı
    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private Map<String, Map<String, Double>> characterPositions = new ConcurrentHashMap<>();  // Karakter Pozisyonları
    private Map<String, String> characterMoveVectors = new ConcurrentHashMap<>();  // Hareket Vektörleri

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(session.getId(), session);
        System.out.println("Yeni bağlantı: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session.getId());
        System.out.println("Bağlantı kapandı: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String receivedData = message.getPayload();
        System.out.println("Gelen veri: " + receivedData);

        String response = processInputData(receivedData);
        if (response != null) {
            broadcast(response);
        }
    }
    private String processInputData(String data) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> inputData = objectMapper.readValue(data, Map.class);

            String characterId = (String) inputData.get("id");
            String action = (String) inputData.get("action");

            if (characterId == null || characterId.isEmpty()) {
                return "{\"status\":\"error\",\"message\":\"Character ID is missing\"}";
            }
            if(action.equals("ping")) {
                //System.out.println("Ping Test Data Get");
                return "ping";
            }

            // Log'lar ekleyerek kontrol edin
            //System.out.println("Character ID: " + characterId);
            //System.out.println("Action: " + action);

            characterPositions.putIfAbsent(characterId, createDefaultPosition());
            Map<String, Double> position = characterPositions.get(characterId);
            characterMoveVectors.put(characterId,action);
            //System.out.println("character Move Vector Anlik olrak "+characterMoveVectors.get(characterId));
            startTimer();

            // Pozisyonu güncelle
            updatePosition(characterId, position);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("id", characterId);
            response.put("updatedPosition", position);
            response.put("allPositions", characterPositions);

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\":\"error\",\"message\":\"Invalid data format\"}";
        }
    }

    private void broadcast(String message) {
        for (WebSocketSession session : sessions.values()) {
            try {
                session.sendMessage(new TextMessage(message));

                System.out.println("Message sent: " + message);  // Gönderilen mesajı konsola yazdır
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private Map<String, Double> createDefaultPosition() {
        Map<String, Double> defaultPosition = new HashMap<>();
        defaultPosition.put("x", 0.0);
        defaultPosition.put("y", 0.0);
        defaultPosition.put("z", 0.0);
        return defaultPosition;
    }

    private void updatePosition(String characterId, Map<String, Double> position) {
        boolean positionChanged = false;

        String action = characterMoveVectors.get(characterId);

        // Karakterin hareket yönünü kontrol et ve pozisyonunu güncelle
        switch (action) {
            case "initialize":
                position = createDefaultPosition();
                positionChanged = true;
                break;
            case "move_forward":
                position.put("z", position.get("z") + characterSpeed);  // z ekseninde ileri git
                positionChanged = true;
                break;
            case "move_backward":
                position.put("z", position.get("z") - characterSpeed);  // z ekseninde geri git
                positionChanged = true;
                break;
            case "move_left":
                position.put("x", position.get("x") - characterSpeed);  // x ekseninde sola git
                positionChanged = true;
                break;
            case "move_right":
                position.put("x", position.get("x") + characterSpeed);  // x ekseninde sağa git
                positionChanged = true;
                break;
            case "move_forward_left":
                position.put("x", position.get("x") - characterSpeed);  // x ekseninde sola git
                position.put("z", position.get("z") + characterSpeed);  // z ekseninde ileri git
                positionChanged = true;
                break;
            case "move_forward_right":
                position.put("x", position.get("x") + characterSpeed);  // x ekseninde sağa git
                position.put("z", position.get("z") + characterSpeed);  // z ekseninde ileri git
                positionChanged = true;
                break;
            case "move_backward_left":
                position.put("x", position.get("x") - characterSpeed);  // x ekseninde sola git
                position.put("z", position.get("z") - characterSpeed);  // z ekseninde geri git
                positionChanged = true;
                break;
            case "move_backward_right":
                position.put("x", position.get("x") + characterSpeed);  // x ekseninde sağa git
                position.put("z", position.get("z") - characterSpeed);  // z ekseninde geri git
                positionChanged = true;
                break;
            case "stop":
                // Stop durumunda hareket duracak
                break;
        }
        if (positionChanged) {
            broadcastUpdatedPosition(characterId, position);
        }
    }



    private void broadcastUpdatedPosition(String characterId, Map<String, Double> position) {
        //System.out.println("New Position Updated In broadcastUpdatedPosition");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("id", characterId);
        response.put("updatedPosition", position);
        response.put("allPositions", characterPositions); // Tüm pozisyonları ekleyebilirsiniz.

        try {
            String message = objectMapper.writeValueAsString(response);
            broadcast(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public GameWebSocketHandler() {
        this.timer = new Timer();
    }
    private boolean timerStarted = false;  // Timer'ın daha önce başlatılıp başlatılmadığını kontrol etmek için

    private void startTimer() {
        if (!timerStarted) {  // Eğer timer daha önce başlatılmamışsa
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    for (Map.Entry<String, String> entry : characterMoveVectors.entrySet()) {
                        String characterId = entry.getKey();
                        String action = entry.getValue();
                        Map<String, Double> position = characterPositions.get(characterId);
                        if (position == null) {
                            position = createDefaultPosition();
                            characterPositions.put(characterId, position);
                        }

                        if (action != null && !action.equals("stop")) {
                            updatePosition(characterId, position);
                        } else {
                            //System.out.println("Character stopped");
                        }
                    }
                }
            }, 0, 100);
            timerStarted = true;  // Timer başlatıldı
        }
    }

    public void stopTimer() {
        timer.cancel();  // Timer'ı durdurur
    }
}