package com.paintapp.paintbackend.model.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SavePaintingRequest {
    private String title;

    @JsonProperty("canvas_data")
    private String canvasData;
}