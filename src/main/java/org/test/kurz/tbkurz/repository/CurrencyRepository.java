package org.test.kurz.tbkurz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.test.kurz.tbkurz.model.entity.Currency;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findCurrencyByCurrencyCodeIgnoreCase(String currencyCode);

}
