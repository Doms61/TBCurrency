package org.test.kurz.tbkurz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.test.kurz.tbkurz.exception.ResourceNotFoundException;
import org.test.kurz.tbkurz.mapper.CurrencyMapper;
import org.test.kurz.tbkurz.model.dto.CurrencyDTO;
import org.test.kurz.tbkurz.model.entity.Currency;
import org.test.kurz.tbkurz.repository.CurrencyRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    public List<CurrencyDTO> getAllCurrencies() {
        return currencyRepository.findAll()
                .stream()
                .map(currencyMapper::currencyToDTO)
                .toList();
    }

    public CurrencyDTO convert(String currencyCode, Double amount) {
        Currency currency = currencyRepository.findCurrencyByCurrencyCodeIgnoreCase(currencyCode).orElseThrow(() -> new ResourceNotFoundException(currencyCode));
        return CurrencyDTO.builder().currencyCode("EUR").amount(calculateValue(currency.getExchangeRate(), amount)).build();
    }

    private Double calculateValue(Double rate, Double amount) {
        // Perform the division using BigDecimal for precision
        BigDecimal exchangeRateBD = BigDecimal.valueOf(rate);
        BigDecimal amountBD = BigDecimal.valueOf(amount);
        return amountBD.divide(exchangeRateBD, 3, RoundingMode.DOWN).doubleValue(); // 3 decimal places
    }
}
