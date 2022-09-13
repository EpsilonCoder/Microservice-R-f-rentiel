package gouv.sn.domain;

import static org.assertj.core.api.Assertions.assertThat;

import gouv.sn.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeAutoriteContractanteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeAutoriteContractante.class);
        TypeAutoriteContractante typeAutoriteContractante1 = new TypeAutoriteContractante();
        typeAutoriteContractante1.setId(1L);
        TypeAutoriteContractante typeAutoriteContractante2 = new TypeAutoriteContractante();
        typeAutoriteContractante2.setId(typeAutoriteContractante1.getId());
        assertThat(typeAutoriteContractante1).isEqualTo(typeAutoriteContractante2);
        typeAutoriteContractante2.setId(2L);
        assertThat(typeAutoriteContractante1).isNotEqualTo(typeAutoriteContractante2);
        typeAutoriteContractante1.setId(null);
        assertThat(typeAutoriteContractante1).isNotEqualTo(typeAutoriteContractante2);
    }
}
