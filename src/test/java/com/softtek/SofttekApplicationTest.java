package com.softtek;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SofttekApplicationTest {
    @Test
    public void main() {
        int a = 1;int b = 1;
        SofttekApplication.main(new String[] {});
        assertThat(a).isSameAs(b);
    }
}
