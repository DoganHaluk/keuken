package be.vdab.keuken.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class ArtikelTest {
    private Artikel artikel;

    @BeforeEach
    void beforeEach() {
        artikel = new Artikel("test", BigDecimal.valueOf(10), BigDecimal.valueOf(20));
    }

    @Test
    void verhoogVerkoopPrijs(){
        artikel.verhoogVerkoopPrijs(BigDecimal.ONE);
        assertThat(artikel.getVerkoopprijs()).isEqualByComparingTo("21");
    }

    @Test
    void verhoogVerkoopPrijsMetNullMislukt(){
        assertThatNullPointerException().isThrownBy(()->artikel.verhoogVerkoopPrijs(null));
    }

    @Test
    void verhoogVerkoopPrijsMetMetNegatieveWaardeMislukt(){
        assertThatIllegalArgumentException().isThrownBy(()->artikel.verhoogVerkoopPrijs(BigDecimal.valueOf(-1)));
    }
}
