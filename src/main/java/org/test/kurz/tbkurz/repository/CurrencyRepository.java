package org.test.kurz.tbkurz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.test.kurz.tbkurz.model.entity.Currency;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findCurrencyByCurrencyCodeIgnoreCase(String currencyCode);

    @Transactional
    @Modifying
    @Query("delete from Currency c where upper(c.currencyCode) = upper(:currencyCode)")
    void delteByCurrencyCode(String currencyCode);
}
