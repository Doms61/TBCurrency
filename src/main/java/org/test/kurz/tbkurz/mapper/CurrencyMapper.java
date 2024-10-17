package org.test.kurz.tbkurz.mapper;

import org.mapstruct.Mapper;
import org.test.kurz.tbkurz.model.dto.CurrencyDTO;
import org.test.kurz.tbkurz.model.entity.Currency;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyDTO currencyToDTO(Currency currency);
    Currency dtoToEntity(CurrencyDTO currencyDTO);

}

