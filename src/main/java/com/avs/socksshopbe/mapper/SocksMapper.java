package com.avs.socksshopbe.mapper;

import com.avs.socksshopbe.dto.SocksDto;
import com.avs.socksshopbe.entity.Socks;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SocksMapper {
    Socks socksDtoToEntity(SocksDto socksDto);
    SocksDto socksEntityToDto(Socks socks);
}
