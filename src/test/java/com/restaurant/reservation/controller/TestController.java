package com.restaurant.reservation.controller;

import com.restaurant.reservation.api.controller.test.TestApiController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestApiController.class)
//@MockBean(JpaMetamodelMappingContext.class)
public class TestController {

    @Autowired
    MockMvc mockMvc;
    
    @Test
    public void MockMvc_초기테스트() throws Exception{
        
        // given
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/string/test")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string("Get/string/test"));

        // when
        
        // then
    }

}
