package org.test.kurz.tbkurz.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.test.kurz.tbkurz.model.dto.CurrencyDTO;
import org.test.kurz.tbkurz.model.entity.Currency;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CurrencyMapperTest {

    @InjectMocks
    private CurrencyMapper currencyMapper = Mappers.getMapper(CurrencyMapper.class);

    @Test
    void testCurrencyToCurrencyDTO() {
        Currency currency = Currency.builder().id(1L).currencyCode("AUD").exchangeRate(1.157).build();

        CurrencyDTO currencyDTO = currencyMapper.currencyToDTO(currency);

        assertAll(
                () -> assertEquals(currency.getCurrencyCode(), currencyDTO.getCurrencyCode()),
                () -> assertEquals(currency.getExchangeRate(), currencyDTO.getExchangeRate())
        );
    }

    @Test
    void testCurrencyDTOToCurrency() {
        CurrencyDTO currencyDTO = CurrencyDTO.builder().currencyCode("EUR").exchangeRate(1.0).build();

        Currency currency = currencyMapper.dtoToEntity(currencyDTO);

        assertAll(
                () -> assertEquals(currencyDTO.getCurrencyCode(), currency.getCurrencyCode()),
                () -> assertEquals(currencyDTO.getExchangeRate(), currency.getExchangeRate())
        );
    }
}
