package com.hackathon.hackthefuture.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hackathon.hackthefuture.web.rest.TestUtil;

public class DemandTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Demand.class);
        Demand demand1 = new Demand();
        demand1.setId(1L);
        Demand demand2 = new Demand();
        demand2.setId(demand1.getId());
        assertThat(demand1).isEqualTo(demand2);
        demand2.setId(2L);
        assertThat(demand1).isNotEqualTo(demand2);
        demand1.setId(null);
        assertThat(demand1).isNotEqualTo(demand2);
    }
}
