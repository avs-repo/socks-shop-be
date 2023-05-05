package com.avs.socksshopbe.controller;

import com.avs.socksshopbe.dto.Operations;
import com.avs.socksshopbe.dto.SocksDto;
import com.avs.socksshopbe.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/socks")
public class StorageController {
    private final StorageService storageService;

    @Operation(
            summary = "incomeSocks",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Удалось добавить приход", content = @Content()),
                    @ApiResponse(responseCode = "400",
                            description = "Параметры запроса отсутствуют или имеют некорректный формат", content = @Content()),
                    @ApiResponse(responseCode = "500",
                            description = "Произошла ошибка, не зависящая от вызывающей стороны", content = @Content())
            }
    )
    @PostMapping("/income")
    public SocksDto incomeSocks(@RequestBody @Validated SocksDto socksDto) {
        log.info("Добавление носков {} на склад", socksDto);
        return storageService.incomeSocks(socksDto);
    }

    @Operation(
            summary = "outcomeSocks",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Удалось отпустить носки со склада", content = @Content()),
                    @ApiResponse(responseCode = "400",
                            description = "Параметры запроса отсутствуют или имеют некорректный формат", content = @Content()),
                    @ApiResponse(responseCode = "500",
                            description = "Произошла ошибка, не зависящая от вызывающей стороны", content = @Content())
            }
    )
    @PostMapping("/outcome")
    public SocksDto outcomeSocks(@RequestBody @Validated SocksDto socksDto) {
        log.info("Забор носков {} со склада", socksDto);
        return storageService.outcomeSocks(socksDto);
    }

    @Operation(
            summary = "getSockByParams",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Запрос выполнен", content = @Content()),
                    @ApiResponse(responseCode = "400",
                            description = "Параметры запроса отсутствуют или имеют некорректный формат", content = @Content()),
                    @ApiResponse(responseCode = "500",
                            description = "Произошла ошибка, не зависящая от вызывающей стороны", content = @Content())
            }
    )
    @GetMapping
    public Integer getSocksByParams(@RequestParam String color,
                                    @RequestParam(defaultValue = "equal") Operations operation,
                                    @PositiveOrZero @NotNull Byte cottonPart) {
        log.info("Запрос количества носков цвета {} с содержанием хлопка {} {}.", color, operation, cottonPart);
        return storageService.getSocksByParams(color, operation, cottonPart);
    }
}
