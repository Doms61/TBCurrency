package org.test.kurz.tbkurz.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.test.kurz.tbkurz.exception.ResourceNotFoundException;
import org.test.kurz.tbkurz.mapper.CurrencyMapper;
import org.test.kurz.tbkurz.model.dto.CurrencyDTO;
import org.test.kurz.tbkurz.model.entity.Currency;
import org.test.kurz.tbkurz.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @Spy
    private CurrencyMapper currencyMapper = Mappers.getMapper(CurrencyMapper.class);

    @InjectMocks
    private CurrencyService currencyService;

    @Test
    void testGetAllCurrencies() {
        Currency currency = Currency.builder().id(1L).currencyCode("AUD").exchangeRate(1.157).build();
        CurrencyDTO currencyDTO = CurrencyDTO.builder().currencyCode("AUD").exchangeRate(1.157).build();

        when(currencyRepository.findAll()).thenReturn(List.of(currency));

        List<CurrencyDTO> result = currencyService.getAllCurrencies();

        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals(currencyDTO.getCurrencyCode(), result.get(0).getCurrencyCode()),
                () -> assertEquals(currencyDTO.getExchangeRate(), result.get(0).getExchangeRate())
        );
    }

    @Test
    void testConvert() {
        String currencyCode = "AUD";
        Double amount = 115.7;
        Currency currency = Currency.builder().id(1L).currencyCode("AUD").exchangeRate(1.157).build();
        CurrencyDTO currencyDTO = CurrencyDTO.builder().currencyCode("EUR").amount(100.0).build();

        when(currencyRepository.findCurrencyByCurrencyCodeIgnoreCase(currencyCode)).thenReturn(Optional.of(currency));

        CurrencyDTO result = currencyService.convert(currencyCode, amount);

        assertAll(
                () -> assertEquals(currencyDTO.getCurrencyCode(), result.getCurrencyCode()),
                () -> assertEquals(currencyDTO.getAmount(), result.getAmount())
        );
    }

    @Test
    void testConvertResourceNotFound() {
        String currencyCode = "AUD";
        Double amount = 115.7;

        when(currencyRepository.findCurrencyByCurrencyCodeIgnoreCase(currencyCode)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> currencyService.convert(currencyCode, amount));
    }
}
