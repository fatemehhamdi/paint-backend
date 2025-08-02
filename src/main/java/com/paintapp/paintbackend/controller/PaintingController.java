package com.paintapp.paintbackend.controller;

import com.paintapp.paintbackend.model.Painting;
import com.paintapp.paintbackend.model.User;
import com.paintapp.paintbackend.service.PaintingService;
import com.paintapp.paintbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/paintings")
@CrossOrigin(origins = "http://localhost:5173")
public class PaintingController {

    @Autowired
    private PaintingService paintingService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> savePainting(@RequestBody Map<String, String> request, Authentication auth) {
        try {
            String username = auth.getName();
            Optional<User> userOpt = userService.findByUsername(username);

            if (!userOpt.isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
            }

            User user = userOpt.get();
            String title = request.get("title");
            String shapes = request.get("shapes");

            Painting painting = paintingService.saveOrUpdatePainting(user, title, shapes);

            return ResponseEntity.ok(Map.of(
                    "id", painting.getId(),
                    "title", painting.getTitle(),
                    "message", "Painting saved successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getPainting(Authentication auth) {
        try {
            String username = auth.getName();
            Optional<User> userOpt = userService.findByUsername(username);

            if (!userOpt.isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
            }

            User user = userOpt.get();
            Optional<Painting> paintingOpt = paintingService.getPaintingByUser(user);

            if (!paintingOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Painting painting = paintingOpt.get();
            return ResponseEntity.ok(Map.of(
                    "id", painting.getId(),
                    "title", painting.getTitle(),
                    "shapes", painting.getShapes(),
                    "createdAt", painting.getCreatedAt(),
                    "updatedAt", painting.getUpdatedAt()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
