package ro.ctce.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.ctce.web.rest.TestUtil;

public class FirmaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Firma.class);
        Firma firma1 = new Firma();
        firma1.setId(1L);
        Firma firma2 = new Firma();
        firma2.setId(firma1.getId());
        assertThat(firma1).isEqualTo(firma2);
        firma2.setId(2L);
        assertThat(firma1).isNotEqualTo(firma2);
        firma1.setId(null);
        assertThat(firma1).isNotEqualTo(firma2);
    }
}
