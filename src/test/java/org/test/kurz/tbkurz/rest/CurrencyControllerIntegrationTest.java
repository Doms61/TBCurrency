package org.test.kurz.tbkurz.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class CurrencyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllCurrencies() throws Exception {
        mockMvc.perform(get("/currency/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].currencyCode").value("AUD"))
                .andExpect(jsonPath("$[0].exchangeRate").value(1.157))
                .andExpect(jsonPath("$[1].currencyCode").value("CZK"))
                .andExpect(jsonPath("$[1].exchangeRate").value(25.256));
    }

    @Test
    void testConvertCurrency() throws Exception {
        mockMvc.perform(get("/currency/convert/CZK/2525"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currencyCode").value("EUR"))
                .andExpect(jsonPath("$.amount").value(99.976));
    }

    @Test
    void testNonExistentCurrency() throws Exception {
        mockMvc.perform(get("/currency/convert/XYZ/1000"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Currency XYZ was not found."));
    }
}
