package ro.ctce.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.ctce.web.rest.TestUtil;

public class OcolTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ocol.class);
        Ocol ocol1 = new Ocol();
        ocol1.setId(1L);
        Ocol ocol2 = new Ocol();
        ocol2.setId(ocol1.getId());
        assertThat(ocol1).isEqualTo(ocol2);
        ocol2.setId(2L);
        assertThat(ocol1).isNotEqualTo(ocol2);
        ocol1.setId(null);
        assertThat(ocol1).isNotEqualTo(ocol2);
    }
}
