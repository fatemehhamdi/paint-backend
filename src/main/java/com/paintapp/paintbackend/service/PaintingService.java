package com.paintapp.paintbackend.service;

import com.paintapp.paintbackend.model.Painting;
import com.paintapp.paintbackend.model.User;
import com.paintapp.paintbackend.repository.PaintingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaintingService {

    @Autowired
    private PaintingRepository paintingRepository;

    public Painting saveOrUpdatePainting(User user, String title, String canvasData) {
        // Check if user already has a painting
        Optional<Painting> existingPainting = paintingRepository.findByUser(user);

        Painting painting;
        if (existingPainting.isPresent()) {
            // Update existing painting
            painting = existingPainting.get();
            painting.setTitle(title);
            painting.setCanvas_data(canvasData);
        } else {
            // Create new painting
            painting = new Painting();
            painting.setTitle(title);
            painting.setCanvas_data(canvasData);
            painting.setUser(user);
        }

        return paintingRepository.save(painting);
    }

    public Optional<Painting> getPaintingByUser(User user) {
        return paintingRepository.findByUser(user);
    }

    public void deletePaintingByUser(User user) {
        Optional<Painting> painting = paintingRepository.findByUser(user);
        if (painting.isPresent()) {
            paintingRepository.delete(painting.get());
        }
    }
}
