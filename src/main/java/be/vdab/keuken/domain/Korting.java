package be.vdab.keuken.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Access(AccessType.FIELD)
public class Korting {
    private int vanafAantal;
    private BigDecimal percentage;

    public Korting(int vanafAantal, BigDecimal percentage) {
        this.vanafAantal = vanafAantal;
        this.percentage = percentage;
    }

    protected Korting() {
    }

    public int getVanafAantal() {
        return vanafAantal;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Korting) {
            var andereKorting = (Korting) object;
            return vanafAantal == andereKorting.vanafAantal;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return vanafAantal;
    }
}
