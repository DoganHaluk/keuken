package be.vdab.keuken.services;

import be.vdab.keuken.domain.Artikel;
import be.vdab.keuken.domain.ArtikelGroep;
import be.vdab.keuken.domain.FoodArtikel;
import be.vdab.keuken.exceptions.ArtikelNietGevondenException;
import be.vdab.keuken.repositories.ArtikelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefaultArtikelServiceTest {
    private DefaultArtikelService service;
    @Mock
    private ArtikelRepository repository;
    private Artikel artikel;
    private ArtikelGroep artikelGroep;

    @BeforeEach
    void beforeEach() {
        service = new DefaultArtikelService(repository);
        artikelGroep = new ArtikelGroep("test");
        artikel = new FoodArtikel("test", BigDecimal.valueOf(10), BigDecimal.valueOf(20), 10, artikelGroep);
    }

    @Test
    void verhoogVerkoopPrijs() {
        when(repository.findById(1)).thenReturn(Optional.of(artikel));
        service.verhoogVerkoopPrijs(1, BigDecimal.ONE);
        assertThat(artikel.getVerkoopprijs()).isEqualByComparingTo("21");
        verify(repository).findById(1);
    }

    @Test
    void verhoogVerkoopPrijsVoorOnbestaandArtikel() {
        assertThatExceptionOfType(ArtikelNietGevondenException.class).isThrownBy(() -> service.verhoogVerkoopPrijs(-1, BigDecimal.valueOf(20)));
        verify(repository).findById(-1);
    }
}
