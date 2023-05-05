package com.avs.socksshopbe.repository;

import com.avs.socksshopbe.entity.Socks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocksRepository extends JpaRepository<Socks, Long> {
    Optional<Socks> findByColorAndCottonPart(String color, Byte cottonPart);

    @Query(value = "SELECT SUM(quantity) AS sum FROM socks s " +
            "WHERE (s.color = :sColor AND s.cotton_part > :cotton)",
            nativeQuery = true)
    Integer sumByColorAndCottonPartMoreThan(@Param("sColor") String searchColor, @Param("cotton") Byte cottonPart);

    @Query(value = "SELECT SUM(quantity) AS sum FROM socks s " +
            "WHERE (s.color = :sColor AND s.cotton_part < :cotton) ",
            nativeQuery = true)
    Integer sumByColorAndCottonPartLessThan(@Param("sColor") String searchColor, @Param("cotton") Byte cottonPart);

    @Query(value = "SELECT SUM(quantity) AS sum FROM socks s " +
            "WHERE (s.color = :sColor AND s.cotton_part = :cotton)",
            nativeQuery = true)
    Integer sumByColorAndCottonPartEquals(@Param("sColor") String searchColor, @Param("cotton") Byte cottonPart);

}
