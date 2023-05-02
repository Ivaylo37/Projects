package org.scalefocus.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.scalefocus.model.Review;
import org.scalefocus.service.ReviewService;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
public class ReviewControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(reviewController)
                .build();
    }

    @Test
    public void getAllBusinesses_singleBusiness_success() throws Exception {
        Review review = new Review(1, 1, 1, 1, "test", LocalDate.now());
        when(reviewService.getAllReviews()).thenReturn(Collections.singletonList(review));

        mockMvc.perform(get("/review"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(review.getId()))
                .andExpect(jsonPath("$[0].user").value(review.getUser()))
                .andExpect(jsonPath("$[0].business").value(review.getBusiness()))
                .andExpect(jsonPath("$[0].rating").value(review.getRating()))
                .andExpect(jsonPath("$[0].feedback").value(review.getFeedback()));
    }
}