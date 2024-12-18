package org.test.kurz.tbkurz.rest;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public ResponseEntity<CurrencyDTO> createCurrency(@RequestBody CurrencyDTO currencyDTO) {
        return ResponseEntity.ok(currencyService.createCurrency(currencyDTO));
    }

    @Hidden
    @DeleteMapping("/delete/{code}")
    public ResponseEntity<CurrencyDTO> deleteCurrency(@PathVariable String code) {
        currencyService.delete(code);
        return ResponseEntity.ok().build();
    }

}
