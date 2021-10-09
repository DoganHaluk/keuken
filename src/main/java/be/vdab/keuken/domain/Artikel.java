package be.vdab.keuken.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "artikels")
@DiscriminatorColumn(name = "soort")
public abstract class Artikel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    private BigDecimal aankoopprijs;
    private BigDecimal verkoopprijs;
    @ElementCollection
    @CollectionTable(name = "kortingen", joinColumns = @JoinColumn(name = "artikelId"))
    @OrderBy("vanafAantal")
    private Set<Korting> kortingen;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "artikelgroepId")
    private ArtikelGroep artikelGroep;

    public Artikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, ArtikelGroep artikelGroep) {
        this.naam = naam;
        this.aankoopprijs = aankoopprijs;
        this.verkoopprijs = verkoopprijs;
        this.kortingen = new LinkedHashSet<>();
        setArtikelgroep(artikelGroep);
    }

    protected Artikel() {
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public BigDecimal getAankoopprijs() {
        return aankoopprijs;
    }

    public BigDecimal getVerkoopprijs() {
        return verkoopprijs;
    }

    public void verhoogVerkoopPrijs(BigDecimal bedrag) {
        if (bedrag.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        verkoopprijs = verkoopprijs.add(bedrag);
    }

    public Set<Korting> getKortingen() {
        return Collections.unmodifiableSet(kortingen);
    }

    public ArtikelGroep getArtikelGroep() {
        return artikelGroep;
    }

    public void setArtikelgroep(ArtikelGroep artikelGroep) {
        if (!artikelGroep.getArtikels().contains(this)) {
            artikelGroep.add(this);
        }
        this.artikelGroep = artikelGroep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artikel)) return false;
        Artikel artikel = (Artikel) o;
        return Objects.equals(naam, artikel.naam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naam);
    }
}
