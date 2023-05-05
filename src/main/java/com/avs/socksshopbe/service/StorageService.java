package com.avs.socksshopbe.service;

import com.avs.socksshopbe.dto.Operations;
import com.avs.socksshopbe.dto.SocksDto;

public interface StorageService {
    SocksDto incomeSocks(SocksDto socksDto);

    SocksDto outcomeSocks(SocksDto socksDto);

    Integer getSocksByParams(String color, Operations operation, Byte cottonPart);
}
