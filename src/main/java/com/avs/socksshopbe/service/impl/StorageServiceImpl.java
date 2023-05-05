package com.avs.socksshopbe.service.impl;

import com.avs.socksshopbe.dto.Operations;
import com.avs.socksshopbe.dto.SocksDto;
import com.avs.socksshopbe.entity.Socks;
import com.avs.socksshopbe.exception.NotEnoughtItemsException;
import com.avs.socksshopbe.exception.NotFoundException;
import com.avs.socksshopbe.mapper.SocksMapper;
import com.avs.socksshopbe.repository.SocksRepository;
import com.avs.socksshopbe.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StorageServiceImpl implements StorageService {

    private final SocksRepository socksRepository;
    private final SocksMapper socksMapper;

    @Override
    @Transactional
    public SocksDto incomeSocks(SocksDto socksDto) {
        Socks socks = socksRepository.findByColorAndCottonPart(socksDto.getColor(), socksDto.getCottonPart())
                .orElse(new Socks(socksDto.getColor(), socksDto.getCottonPart()));
        socks.setQuantity(socks.getQuantity() + socksDto.getQuantity());
        socksRepository.save(socks);
        log.info("Успешно добавлены.");
        return socksMapper.socksEntityToDto(socks);
    }

    @Override
    @Transactional
    public SocksDto outcomeSocks(SocksDto socksDto) {
        Socks socks = socksRepository.findByColorAndCottonPart(socksDto.getColor(), socksDto.getCottonPart())
                .orElseThrow(() -> new NotFoundException("Нужные носки " + socksDto + " для выдачи не найдены."));

        int quantity = socks.getQuantity() - socksDto.getQuantity();

        if (quantity < 0) {
            throw new NotEnoughtItemsException("Носков запрошено " + socksDto.getQuantity()
                    + ", а на складе всего " + socks.getQuantity());
        }

        socks.setQuantity(quantity);
        socksRepository.save(socks);
        log.info("Успешно, осталось на складе: " + socks.getQuantity());
        return socksMapper.socksEntityToDto(socks);
    }

    @Override
    public Integer getSocksByParams(String color, Operations operation, Byte cottonPart) {
        Integer quantity = switch (operation) {
            case equal -> socksRepository.sumByColorAndCottonPartEquals(color, cottonPart);
            case moreThan -> socksRepository.sumByColorAndCottonPartMoreThan(color, cottonPart);
            case lessThan -> socksRepository.sumByColorAndCottonPartLessThan(color, cottonPart);
        };

        if (quantity == null) quantity = 0;
        log.info("Носков соответствующих запросу на складе: " + quantity + " шт.");
        return quantity;
    }
}
