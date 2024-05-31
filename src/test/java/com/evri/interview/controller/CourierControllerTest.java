package com.evri.interview.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CourierControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllCouriers() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/api/couriers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
        assertTrue(responseContent.contains("Ben"));
        assertTrue(responseContent.contains("Askew"));
        assertTrue(responseContent.contains("true"));
        assertTrue(responseContent.contains("Lennon"));
        assertTrue(responseContent.contains("John"));
        assertTrue(responseContent.contains("false"));
    }

    @Test
    public void testGetActiveCouriers() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("/api/couriers?isActive=true").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
        assertTrue(responseContent.contains("Ben"));
        assertTrue(responseContent.contains("Askew"));
        assertTrue(responseContent.contains("true"));
        assertFalse(responseContent.contains("Lennon"));
        assertFalse(responseContent.contains("John"));
        assertFalse(responseContent.contains("false"));
    }

    @Test
    public void testUpdateCourier() throws Exception {

        ResultActions resultActions = this.mockMvc.perform(put("/api/couriers/{courierId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Jane Doe\", \"active\": false}"))
                .andExpect(status().isOk());

        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
        assertTrue(responseContent.contains("Jane"));
        assertTrue(responseContent.contains("Doe"));
        assertTrue(responseContent.contains("false"));
    }

    @Test
    public void testUpdateCourier_noCourierWithRequestId() throws Exception {

        this.mockMvc.perform(put("/api/couriers/{courierId}", 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Jane Doe\", \"active\": false}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateCourier_invalidName() throws Exception {

        this.mockMvc.perform(put("/api/couriers/{courierId}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Jane\", \"active\": false}"))
                .andExpect(status().is4xxClientError());
    }
}