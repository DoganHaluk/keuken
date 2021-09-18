package be.vdab.keuken.repositories;

import be.vdab.keuken.domain.Artikel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@Sql("/insertArtikel.sql")
@Import(JpaArtikelRepository.class)
class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final JpaArtikelRepository repository;
    private final EntityManager manager;
    private static final String ARTIKELS = "artikels";
    private Artikel artikel;

    public JpaArtikelRepositoryTest(JpaArtikelRepository repository, EntityManager manager) {
        this.repository = repository;
        this.manager = manager;
    }

    private long idVanTestArtikel() {
        return jdbcTemplate.queryForObject("SELECT id FROM artikels WHERE naam='test'", Long.class);
    }

    @Test
    void findById() {
        assertThat(repository.findById(idVanTestArtikel())).hasValueSatisfying(artikel -> assertThat(artikel.getNaam()).isEqualTo("test"));
    }

    @Test
    void findByOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @BeforeEach
    void beforeEach() {
        artikel = new Artikel("test", BigDecimal.ONE, BigDecimal.ONE);
    }

    @Test
    void create() {
        repository.create(artikel);
        assertThat(artikel.getId()).isPositive();
        assertThat(countRowsInTableWhere(ARTIKELS, "id=" + artikel.getId())).isOne();
    }

    @Test
    void findByNaamContainsEenWoord() {
        var artikels = repository.findByNaamContains("pe");
        assertThat(artikels)
                .hasSize(countRowsInTableWhere(ARTIKELS, "naam like 'pe'"))
                .allSatisfy(naam -> assertThat(naam.getNaam()).containsIgnoringCase("pe"))
                .isSorted();
    }

    @Test
    void verhoogAlleVerkoopPrijzen() {
        assertThat(repository.verhoogAlleVerkoopPrijzen(BigDecimal.TEN)).isEqualTo(countRowsInTable(ARTIKELS));
        assertThat(countRowsInTableWhere(ARTIKELS, "verkoopprijs = 1.1 and id = " + idVanTestArtikel())).isOne();
    }
}
