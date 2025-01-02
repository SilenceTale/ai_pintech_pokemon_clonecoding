package org.koreait.dl.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles({"default", "dl"})
@AutoConfigureMockMvc // 통합 테스트를 하기 위한 어노테이션
public class ApiDlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void sentimentTest() throws Exception {

        mockMvc.perform(post("/api/dl/sentiment")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED) // 일반적인 양식 형태
                        .param("items", "재미없다", "재미있음") // param 안에 데이터를 넣음!
                        .with(csrf().asHeader())
                )
                .andDo(print());
    }
}
