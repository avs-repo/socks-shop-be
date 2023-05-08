package com.avs.socksshopbe.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocksDto {
    @NotBlank
    @NotNull
    @Size(max = 255)
    private String color;
    @NotNull
    @Min(0)
    @Max(100)
    private Byte cottonPart;
    @NotNull
    @Positive
    private Integer quantity;
}
