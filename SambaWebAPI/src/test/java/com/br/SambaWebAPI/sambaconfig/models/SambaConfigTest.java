package com.br.SambaWebAPI.sambaconfig.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SambaConfigTest {

    @Test
    @DisplayName("Should create SambaConfig object")
    void shouldCreateSambaConfigObject() {
        SambaConfig sambaConfig = new SambaConfig();
        assertNotNull(sambaConfig);
    }

    @Test
    @DisplayName("Should set and get section")
    void shouldSetAndGetSection() {
        SambaConfig sambaConfig = new SambaConfig();
        sambaConfig.setSection("section");
        assertEquals("section", sambaConfig.getSection());
    }

    @Test
    @DisplayName("Should set and get option")
    void shouldSetAndGetOption() {
        SambaConfig sambaConfig = new SambaConfig();
        List<String> optionList = new ArrayList<>();
        optionList.add("option");
        sambaConfig.setSectionParams(optionList);

        assertEquals(optionList, sambaConfig.getSectionParams());
    }


}