package com.paintapp.paintbackend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paintapp.paintbackend.model.DTO.LoadPaintingResponse;
import com.paintapp.paintbackend.model.DTO.SavePaintingRequest;
import com.paintapp.paintbackend.model.Painting;
import com.paintapp.paintbackend.model.User;
import com.paintapp.paintbackend.service.PaintingService;
import com.paintapp.paintbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/paintings")
//@CrossOrigin(origins = "http://localhost:5173")
public class PaintingController {

    @Autowired
    private PaintingService paintingService;

    @Autowired
    private UserService userService;

//    // Test endpoint (demonstrates correct body mapping)
//    @PostMapping("/test")
//    public ResponseEntity<?> test(@RequestBody SavePaintingRequest request){
//        return ResponseEntity.ok("salasdf");
//    }
//
//    // POST /api/paintings
//    // POST /api/paintings
    @PostMapping
    public ResponseEntity<?> savePainting(@RequestBody SavePaintingRequest request, Authentication auth) {
        try {
            String username = auth.getName();
            Optional<User> userOpt = userService.findByUsername(username);

            if (!userOpt.isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
            }

            User user = userOpt.get();
            String title = request.getTitle();
            String canvasData = request.getCanvasData(); // Use canvasData here!

            Painting painting = paintingService.saveOrUpdatePainting(user, title, canvasData);

            return ResponseEntity.ok(Map.of(
                    "id", painting.getId(),
                    "title", painting.getTitle(),
                    "message", "Painting saved successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


    // GET /api/paintings

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

            LoadPaintingResponse loadPaintingResponse = new LoadPaintingResponse();
            loadPaintingResponse.setTitle(painting.getTitle());
            loadPaintingResponse.setCanvasData(painting.getCanvas_data());

            return ResponseEntity.ok(loadPaintingResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
