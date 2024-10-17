package org.test.kurz.tbkurz.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.test.kurz.tbkurz.model.dto.CurrencyDTO;
import org.test.kurz.tbkurz.service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/getAll")
    public ResponseEntity<List<CurrencyDTO>> getAllCurrencies() {
        return ResponseEntity.ok(currencyService.getAllCurrencies());
    }

    @GetMapping("/convert/{currency}/{amount}")
    public ResponseEntity<CurrencyDTO> convertCurrency(@PathVariable String currency, @PathVariable Double amount) {
        return ResponseEntity.ok(currencyService.convert(currency, amount));
    }

}
