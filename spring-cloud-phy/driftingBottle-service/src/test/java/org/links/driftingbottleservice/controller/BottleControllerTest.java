package org.links.driftingbottleservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.links.driftingbottleservice.dto.*;
import org.links.driftingbottleservice.service.BottleService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BottleController.class)
public class BottleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BottleService bottleService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCreateBottle() throws Exception {
        BottleRequest request = new BottleRequest();
        request.setPhoneNumber("13200000000");
        request.setContent("test");

        mockMvc.perform(post("/api/v1/bottle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("漂流瓶发布成功"));
    }

    @Test
    void testPickBottle() throws Exception {
        BottleResponse response = new BottleResponse(1L, 2L, "test", LocalDateTime.now().toString());
        Mockito.when(bottleService.pickBottle("13200000000")).thenReturn(response);

        mockMvc.perform(get("/api/v1/bottle/pick")
                        .param("phoneNumber", "13200000000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.content").value("test"));
    }

    @Test
    void testCommentBottle() throws Exception {
        BottleCommentRequest request = new BottleCommentRequest();
        request.setPhoneNumber("13200000000");
        request.setContent("评论内容");

        mockMvc.perform(post("/api/v1/bottle/1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("评论成功"));
    }

    @Test
    void testDeleteBottle() throws Exception {
        mockMvc.perform(delete("/api/v1/bottle/1")
                        .param("phoneNumber", "13200000000")
                        .param("isAdmin", "false"))
                .andExpect(status().isOk())
                .andExpect(content().string("漂流瓶及其评论已被删除"));
    }

    @Test
    void testRecycleBottle() throws Exception {
        mockMvc.perform(put("/api/v1/bottle/1/recycle")
                        .param("phoneNumber", "13200000000"))
                .andExpect(status().isOk())
                .andExpect(content().string("漂流瓶已回收到背包"));
    }

    @Test
    void testThrowBottle() throws Exception {
        mockMvc.perform(put("/api/v1/bottle/1/throw")
                        .param("phoneNumber", "13200000000"))
                .andExpect(status().isOk())
                .andExpect(content().string("漂流瓶已重新投放到海洋"));
    }

    @Test
    void testGetBottlesByUserAndStatus() throws Exception {
        BottleResponse response = new BottleResponse(1L, 1L, "test", LocalDateTime.now().toString());
        Mockito.when(bottleService.getBottlesByUserAndStatus(any(), any())).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/bottle/list")
                        .param("phoneNumber", "13200000000")
                        .param("status", "IN_OCEAN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("test"));
    }

    @Test
    void testGetBottleComments() throws Exception {
        BottleCommentResponse comment = new BottleCommentResponse(1L, "评论", LocalDateTime.now().toString());
        Mockito.when(bottleService.getBottleComments(1L)).thenReturn(List.of(comment));

        mockMvc.perform(get("/api/v1/bottle/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("评论"));
    }
}