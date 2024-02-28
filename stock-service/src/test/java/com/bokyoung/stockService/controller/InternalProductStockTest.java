package com.bokyoung.stockService.controller;

import com.bokyoung.stockService.application.controller.StockController;
import com.bokyoung.stockService.application.service.ProductStockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ContextConfiguration(classes = StockController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InternalProductStockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductStockService productStockService;

    @Test
    public void 회원가입_성공() throws Exception {
    }
}
