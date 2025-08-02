package com.paintapp.paintbackend.repository;

import com.paintapp.paintbackend.model.Painting;
import com.paintapp.paintbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaintingRepository extends JpaRepository<Painting, Long> {
    Optional<Painting> findByUser(User user);
    void deleteByUser(User user);
}
