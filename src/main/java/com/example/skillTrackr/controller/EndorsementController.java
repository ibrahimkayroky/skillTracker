package com.example.skillTrackr.controller;

import com.example.skillTrackr.service.EndorsementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/endorsements")
@RequiredArgsConstructor
public class EndorsementController {

    private final EndorsementService endorsementService;

    @PostMapping("/{skillId}/endorse/{endorserId}")
    public ResponseEntity<?> endorseSkill(
            @PathVariable Long skillId,
            @PathVariable Long endorserId
    ){
       endorsementService.endorseSkill(skillId, endorserId);
       return ResponseEntity.ok("skill endorsed successfully");
    }

    @GetMapping("/{skillId}/count")
    public ResponseEntity<Long> getEndorsementCount(@PathVariable Long skillId) {
        return ResponseEntity.ok(endorsementService.getEndorsementCount(skillId));
    }

}
