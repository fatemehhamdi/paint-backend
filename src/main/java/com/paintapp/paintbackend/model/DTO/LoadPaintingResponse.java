package com.paintapp.paintbackend.model.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoadPaintingResponse {
    private String title;

    @JsonProperty("canvas_data")
    private String canvasData;
}