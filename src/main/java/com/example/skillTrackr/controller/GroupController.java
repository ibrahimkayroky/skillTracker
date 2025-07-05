package com.example.skillTrackr.controller;

import com.example.skillTrackr.dto.CreateGroupRequest;
import com.example.skillTrackr.dto.GroupMessageDTO;
import com.example.skillTrackr.dto.SendGroupMessageRequest;
import com.example.skillTrackr.model.GroupConversation;
import com.example.skillTrackr.model.GroupMessage;
import com.example.skillTrackr.repository.GroupConversationRepository;
import com.example.skillTrackr.repository.GroupMessageRepository;
import com.example.skillTrackr.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final GroupService groupService;
    private final GroupConversationRepository groupRepo;
    private final GroupMessageRepository messageRepo;

    @PostMapping("/create")
    public ResponseEntity<GroupConversation> createGroup(@RequestBody CreateGroupRequest request) {
        GroupConversation group = groupService.createGroup(
                request.getName(), request.getDescription(), request.getCreator(), request.getMembers());
        return ResponseEntity.ok(group);
    }

    @GetMapping("/{groupId}/messages")
    public ResponseEntity<List<GroupMessage>> getGroupMessages(@PathVariable Long groupId) {
        GroupConversation group = groupRepo.findById(groupId).orElseThrow();
        List<GroupMessage> messages = messageRepo.findByGroupOrderBySentAtAsc(group);
        return ResponseEntity.ok(messages);
    }

//    @PostMapping("/{groupId}/send")
//    public ResponseEntity<GroupMessage> sendMessageToGroup(@RequestBody SendGroupMessageRequest request) {
//        GroupMessage sentMessage = groupService.sendGroupMessage(request);
//        return ResponseEntity.ok(sentMessage);
//    }

    @GetMapping("/my-groups")
    public ResponseEntity<List<GroupConversation>> myGroups(@RequestParam String username) {
        return ResponseEntity.ok(groupService.getGroupsOfUser(username));
    }

    @GetMapping("/public")
    public ResponseEntity<List<GroupConversation>> publicGroups() {
        return ResponseEntity.ok(groupService.getPublicGroups());
    }

    @PostMapping("/{groupId}/join")
    public ResponseEntity<GroupConversation> joinGroup(@PathVariable Long groupId, @RequestParam String username) {
        return ResponseEntity.ok(groupService.joinGroup(groupId, username));
    }

    @PostMapping("/{groupId}/send")
    public ResponseEntity<GroupMessage> sendMessageToGroup(@RequestBody SendGroupMessageRequest request) {
        GroupMessage sentMessage = groupService.sendGroupMessage(request);

        messagingTemplate.convertAndSend(
                "/topic/group/" + request.getGroupId(),
                GroupMessageDTO.builder()
                        .sender(sentMessage.getSender().getEmail())
                        .content(sentMessage.getContent())
                        .sentAt(sentMessage.getSentAt())
                        .groupId(sentMessage.getGroup().getId())
                        .build()
        );

        return ResponseEntity.ok(sentMessage);
    }


}
