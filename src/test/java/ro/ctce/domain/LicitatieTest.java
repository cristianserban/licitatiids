package ro.ctce.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.ctce.web.rest.TestUtil;

public class LicitatieTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Licitatie.class);
        Licitatie licitatie1 = new Licitatie();
        licitatie1.setId(1L);
        Licitatie licitatie2 = new Licitatie();
        licitatie2.setId(licitatie1.getId());
        assertThat(licitatie1).isEqualTo(licitatie2);
        licitatie2.setId(2L);
        assertThat(licitatie1).isNotEqualTo(licitatie2);
        licitatie1.setId(null);
        assertThat(licitatie1).isNotEqualTo(licitatie2);
    }
}
