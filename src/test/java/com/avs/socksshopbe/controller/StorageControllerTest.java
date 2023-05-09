package com.avs.socksshopbe.controller;

import com.avs.socksshopbe.dto.Operations;
import com.avs.socksshopbe.entity.Socks;
import com.avs.socksshopbe.repository.SocksRepository;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class StorageControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    StorageController storageController;
    @Autowired
    SocksRepository socksRepository;

    public static final String DEFAULT_COLOR = "blue";
    public static final Byte DEFAULT_COTTON_PART = 50;
    public static final Integer DEFAULT_QUANTITY = 125;
    private final JSONObject jsonObject = new JSONObject();
    private final JSONObject resultJsonObject = new JSONObject();

    @Test
    public void contextsLoad() {
        assertThat(socksRepository).isNotNull();
        assertThat(storageController).isNotNull();
    }

    @BeforeEach
    public void initJsonObject() {
        socksRepository.deleteAll();

        jsonObject.put("color", DEFAULT_COLOR);
        jsonObject.put("cottonPart", DEFAULT_COTTON_PART);
        jsonObject.put("quantity", DEFAULT_QUANTITY);

        resultJsonObject.put("color", DEFAULT_COLOR);
        resultJsonObject.put("cottonPart", DEFAULT_COTTON_PART);
        resultJsonObject.put("quantity", DEFAULT_QUANTITY);
    }

    @Test
    void incomeSocksTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/socks/income")
                        .content(jsonObject.toJSONString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(resultJsonObject.toJSONString()));
    }

    void incomeSocksToRepoForTest(Integer quantity) {
        Socks socks = new Socks(DEFAULT_COLOR, DEFAULT_COTTON_PART, quantity);
        socksRepository.save(socks);
    }

    @Test
    void incomeSocksIfExistTest() throws Exception {
        incomeSocksToRepoForTest(DEFAULT_QUANTITY);
        resultJsonObject.replace("quantity", DEFAULT_QUANTITY * 2);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/socks/income")
                        .content(jsonObject.toJSONString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(resultJsonObject.toJSONString()));
    }

    @Test
    void incomeSocksWrongCottonPartRequestTest() throws Exception {
        jsonObject.replace("cottonPart", 125);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/socks/income")
                        .content(jsonObject.toJSONString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void incomeSocksWrongQuantityRequestTest() throws Exception {
        jsonObject.replace("quantity", 0);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/socks/income")
                        .content(jsonObject.toJSONString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void outcomeSocksTest() throws Exception {
        incomeSocksToRepoForTest(DEFAULT_QUANTITY * 2);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/socks/outcome")
                        .content(jsonObject.toJSONString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(resultJsonObject.toJSONString()));
    }

    @Test
    void outcomeSocksWrongQuantityTest() throws Exception {
        incomeSocksToRepoForTest(DEFAULT_QUANTITY * 2);
        jsonObject.replace("quantity", DEFAULT_QUANTITY * 3);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/socks/outcome")
                        .content(jsonObject.toJSONString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());

        jsonObject.replace("quantity", -1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/socks/outcome")
                        .content(jsonObject.toJSONString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getSocksByParamsTest() throws Exception {
        incomeSocksToRepoForTest(DEFAULT_QUANTITY);
        socksRepository.save(new Socks(DEFAULT_COLOR, (byte) 55, 300));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/socks")
                        .param("color", DEFAULT_COLOR)
                        .param("operation", String.valueOf(Operations.lessThan))
                        .param("cottonPart", "60"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("425"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/socks")
                        .param("color", DEFAULT_COLOR)
                        .param("operation", String.valueOf(Operations.equal))
                        .param("cottonPart", String.valueOf(DEFAULT_COTTON_PART)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("125"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/socks")
                        .param("color", DEFAULT_COLOR)
                        .param("operation", String.valueOf(Operations.moreThan))
                        .param("cottonPart", String.valueOf(DEFAULT_COTTON_PART)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("300"));
    }
}