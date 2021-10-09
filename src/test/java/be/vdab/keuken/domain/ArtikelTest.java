package be.vdab.keuken.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class ArtikelTest {
    private Artikel artikel1;
    private Artikel artikel2;
    private ArtikelGroep artikelGroep1;
    private ArtikelGroep artikelGroep2;

    @BeforeEach
    void beforeEach() {
        artikelGroep1 = new ArtikelGroep("test");
        artikelGroep2 = new ArtikelGroep("test2");
        artikel1 = new FoodArtikel("test", BigDecimal.valueOf(10), BigDecimal.valueOf(20), 10, artikelGroep1);
        artikel2 = new FoodArtikel("test2", BigDecimal.valueOf(10), BigDecimal.valueOf(20), 10, artikelGroep1);
    }

    @Test
    void verhoogVerkoopPrijs() {
        artikel1.verhoogVerkoopPrijs(BigDecimal.ONE);
        assertThat(artikel1.getVerkoopprijs()).isEqualByComparingTo("21");
    }

    @Test
    void verhoogVerkoopPrijsMetNullMislukt() {
        assertThatNullPointerException().isThrownBy(() -> artikel1.verhoogVerkoopPrijs(null));
    }

    @Test
    void verhoogVerkoopPrijsMetMetNegatieveWaardeMislukt() {
        assertThatIllegalArgumentException().isThrownBy(() -> artikel1.verhoogVerkoopPrijs(BigDecimal.valueOf(-1)));
    }

    @Test
    void meerdereArtikelsKunnenTotDezelfdeArtikelGroepBehoren() {
        assertThat(artikelGroep1.getArtikels()).containsOnly(artikel1, artikel2);
    }

    @Test
    void artikel1KomtVoorInArtikelGroep() {
        assertThat(artikel1.getArtikelGroep()).isEqualTo(artikelGroep1);
        assertThat(artikelGroep1.getArtikels()).contains(artikel1);
    }

    @Test
    void Artikel1VerhuistVanArtikelGroep1NaarArtikelGroep2() {
        artikel1.setArtikelgroep(artikelGroep2);
        assertThat(artikel1.getArtikelGroep()).isEqualTo(artikelGroep2);
        assertThat(artikelGroep1.getArtikels()).doesNotContain(artikel1);
        assertThat(artikelGroep2.getArtikels()).containsOnly(artikel1);
    }

    @Test
    void eenNullArtikelGroepInDeSetterMislukt() {
        assertThatNullPointerException().isThrownBy(() -> artikel1.setArtikelgroep(null));
    }

    @Test
    void nullAlsArtikelGroepInDeConstructorMislukt() {
        assertThatNullPointerException().isThrownBy(() -> new FoodArtikel("test", BigDecimal.ONE, BigDecimal.ONE, 1, null));
    }
}
