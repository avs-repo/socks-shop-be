package com.avs.socksshopbe.service.impl;

import com.avs.socksshopbe.dto.Operations;
import com.avs.socksshopbe.dto.SocksDto;
import com.avs.socksshopbe.entity.Socks;
import com.avs.socksshopbe.exception.NotEnoughItemsException;
import com.avs.socksshopbe.exception.NotFoundException;
import com.avs.socksshopbe.mapper.SocksMapper;
import com.avs.socksshopbe.repository.SocksRepository;
import com.avs.socksshopbe.service.StorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class StorageServiceImpl implements StorageService {

    private final SocksRepository socksRepository;
    private final SocksMapper socksMapper;

    /**
     * Processing socks receipt to storage
     *
     * @param socksDto - containing data (color, cotton part and quantity) of socks
     * @return SocksDto - result of stored socks at storage
     */
    @Override
    @Transactional
    public SocksDto incomeSocks(SocksDto socksDto) {
        Socks socks = socksRepository.findByColorIgnoreCaseAndCottonPart(socksDto.getColor(), socksDto.getCottonPart())
                .orElse(new Socks(socksDto.getColor(), socksDto.getCottonPart()));
        socks.setQuantity(socks.getQuantity() + socksDto.getQuantity());
        socksRepository.save(socks);
        log.info("Успешно добавлены.");
        return socksMapper.socksEntityToDto(socks);
    }

    /**
     * Processing a stock quantity request
     *
     * @param color      - requested socks color
     * @param operation  - comparing operation (equal, lessThan, moreThan)
     * @param cottonPart - cotton content in the socks
     * @return Integer - result of socks quantity at storage
     */
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

    /**
     * Processing socks shipment from storage
     *
     * @param socksDto - containing data (color, cotton part and quantity) of requested socks
     * @return SocksDto - result of stored socks at storage
     */
    @Override
    @Transactional
    public SocksDto outcomeSocks(SocksDto socksDto) {
        Socks socks = socksRepository.findByColorIgnoreCaseAndCottonPart(socksDto.getColor(), socksDto.getCottonPart())
                .orElseThrow(() -> new NotFoundException("Нужные носки " + socksDto + " для выдачи не найдены."));

        int quantity = socks.getQuantity() - socksDto.getQuantity();
        if (quantity < 0) {
            throw new NotEnoughItemsException("Носков запрошено " + socksDto.getQuantity()
                    + ", а на складе всего " + socks.getQuantity());
        }

        socks.setQuantity(quantity);
        socksRepository.save(socks);
        log.info("Успешно, осталось на складе: " + socks.getQuantity());
        return socksMapper.socksEntityToDto(socks);
    }
}
