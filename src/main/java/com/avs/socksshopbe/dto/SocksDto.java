package com.avs.socksshopbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;

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
    @Min(1)
    private Integer quantity;
}
