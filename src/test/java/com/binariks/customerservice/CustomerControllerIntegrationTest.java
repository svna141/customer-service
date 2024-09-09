package com.binariks.customerservice;

import com.binariks.customerservice.controller.CustomerController;
import com.binariks.customerservice.exception.CustomerNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static com.binariks.customerservice.controller.RestConstants.API_PATH;
import static com.binariks.customerservice.controller.RestConstants.CUSTOMERS_PATH;
import static com.binariks.customerservice.controller.RestConstants.HISTORY_PATH;
import static com.binariks.customerservice.controller.RestConstants.ID_PATH;
import static com.binariks.customerservice.exception.ErrorMessage.CUSTOMER_NOT_FOUND;
import static com.binariks.customerservice.util.TestUtil.address2;
import static com.binariks.customerservice.util.TestUtil.asJsonString;
import static com.binariks.customerservice.util.TestUtil.dateOfBirth2;
import static com.binariks.customerservice.util.TestUtil.femaleGender;
import static com.binariks.customerservice.util.TestUtil.getDefaultCustomerRequest;
import static com.binariks.customerservice.util.TestUtil.getUpdatedCustomerRequest;
import static com.binariks.customerservice.util.TestUtil.name2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = CustomerServiceApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class CustomerControllerIntegrationTest {

    @Autowired
    private CustomerController controller;

    @Autowired
    private MockMvc mockMvc;

    private static final Long ID = 1L;
    private static final Long WRONG_ID = 1231211L;

    @Test
    public void whenCustomerControllerInjected_thenNotNull() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void whenGetCustomerById_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + CUSTOMERS_PATH + ID_PATH, ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(ID));
    }

    @Test
    public void whenCreateCustomer_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH + CUSTOMERS_PATH)
                        .content(asJsonString(getDefaultCustomerRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenGetCustomers_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + CUSTOMERS_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)));
    }

    @Test
    public void whenDeleteCustomerById_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(API_PATH + CUSTOMERS_PATH + ID_PATH, ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenGetCustomerByNonExistId_thenThrowException() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + CUSTOMERS_PATH + ID_PATH, WRONG_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomerNotFoundException))
                .andExpect(result -> assertEquals(String.format(CUSTOMER_NOT_FOUND, WRONG_ID),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void whenUpdateCustomer_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch(API_PATH + CUSTOMERS_PATH + ID_PATH, ID)
                        .content(asJsonString(getUpdatedCustomerRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name").value(name2))
                .andExpect(jsonPath("$.dateOfBirth").value(dateOfBirth2.toString()))
                .andExpect(jsonPath("$.address").value(address2))
                .andExpect(jsonPath("$.gender").value(femaleGender));

    }

    @Test
    public void whenUpdateCustomerByNonExistId_thenThrowException() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.patch(API_PATH + CUSTOMERS_PATH + ID_PATH, WRONG_ID)
                        .content(asJsonString(getUpdatedCustomerRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomerNotFoundException))
                .andExpect(result -> assertEquals(String.format(CUSTOMER_NOT_FOUND, WRONG_ID),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void whenGetCustomersHistoryById_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + CUSTOMERS_PATH + ID_PATH + HISTORY_PATH, ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenGetCustomersHistoryByNonExistId_thenThrowException() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + CUSTOMERS_PATH + ID_PATH + HISTORY_PATH, WRONG_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomerNotFoundException))
                .andExpect(result -> assertEquals(String.format(CUSTOMER_NOT_FOUND, WRONG_ID),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }


}