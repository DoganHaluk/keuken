package be.vdab.keuken.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class ArtikelGroepTest {
    private Artikel artikel;
    private ArtikelGroep artikelGroep1;
    private ArtikelGroep artikelGroep2;

    @BeforeEach
    void beforeEach() {
        artikelGroep1 = new ArtikelGroep("test");
        artikelGroep2 = new ArtikelGroep("test2");
        artikel = new FoodArtikel("test", BigDecimal.valueOf(10), BigDecimal.valueOf(20), 10, artikelGroep1);

    }

    @Test
    void artikelGroep1IsDeArtikelGroepVanArtikel() {
        assertThat(artikel.getArtikelGroep()).isEqualTo(artikelGroep1);
        assertThat(artikelGroep1.getArtikels()).containsOnly(artikel);
    }

    @Test
    void artikelVerhuistVanArtikelGroep1NaarArtikelGroep2() {
        assertThat(artikelGroep2.add(artikel)).isTrue();
        assertThat(artikelGroep1.getArtikels()).doesNotContain(artikel);
        assertThat(artikelGroep2.getArtikels()).containsOnly(artikel);
        assertThat(artikel.getArtikelGroep()).isEqualTo(artikelGroep2);
    }

    @Test
    void eenNullArtikelToevoegenMislukt() {
        assertThatNullPointerException().isThrownBy(() -> artikelGroep1.add(null));
    }
}
