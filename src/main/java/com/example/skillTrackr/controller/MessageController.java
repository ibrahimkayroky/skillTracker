package com.example.skillTrackr.controller;

import com.example.skillTrackr.dto.MessageDTO;
import com.example.skillTrackr.model.Message;
import com.example.skillTrackr.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/{senderId}")
    public ResponseEntity<Message> sendMessage(@PathVariable Long senderId, @RequestBody MessageDTO dto){
        return ResponseEntity.ok(messageService.sendMessage(senderId,dto));
    }

    @GetMapping("/conversation/{user1Id}/{user2Id}")
    public ResponseEntity<List<Message>> getConversation(
            @PathVariable Long user1Id, @PathVariable Long user2Id) {
        return ResponseEntity.ok(messageService.getConversation(user1Id, user2Id));
    }

    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<Message>> getUnreadMessages(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.getUnreadMessages(userId));
    }

}
