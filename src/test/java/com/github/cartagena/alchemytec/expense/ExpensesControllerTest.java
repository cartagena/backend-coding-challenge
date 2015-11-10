/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.github.cartagena.alchemytec.expense;

import static com.github.cartagena.alchemytec.expense.Expense.newExpense;
import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cartagena.alchemytec.AlchemytecTestApplication;

@TestPropertySource("classpath:test.properties")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AlchemytecTestApplication.class)
@WebAppConfiguration
public class ExpensesControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private ExpensesController expensesController;

    @Before
    public void setUp() throws Exception {
        mockMvc = standaloneSetup(expensesController)
                .build();
    }

    @Test
    public void testFindAll() throws Exception {
        final ZonedDateTime now = ZonedDateTime.now();
        final String nowString = ISO_INSTANT.format(now);

        repository.save(newExpense()
                .date(now)
                .amount(new BigDecimal(200))
                .vat(new BigDecimal(40))
                .reason("Bills")
                .build());

        repository.save(newExpense()
                .date(now)
                .amount(new BigDecimal(23.54))
                .vat(new BigDecimal(4.71))
                .reason("Dinner")
                .build());

        mockMvc.perform(
                get("/expenses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())

                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].date", equalTo(nowString)))
                .andExpect(jsonPath("$[0].amount", equalTo(200.0)))
                .andExpect(jsonPath("$[0].vat", equalTo(40.0)))
                .andExpect(jsonPath("$[0].reason", equalTo("Bills")))

                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[1].date", equalTo(nowString)))
                .andExpect(jsonPath("$[1].amount", equalTo(23.54)))
                .andExpect(jsonPath("$[1].vat", equalTo(4.71)))
                .andExpect(jsonPath("$[1].reason", equalTo("Dinner")));
    }

    @Test
    public void testCreate() throws Exception {
        final ZonedDateTime now = ZonedDateTime.now();
        final String nowString = ISO_INSTANT.format(now);

        final Expense expense = newExpense()
            .amount(new BigDecimal(100))
            .vat(new BigDecimal(20))
            .reason("Bills")
            .date(now)
            .build();


        mockMvc.perform(
                post("/expenses")
                    .content(json(expense))
                    .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void testCreateWithoutMandatoryField() throws Exception {
        final ZonedDateTime now = ZonedDateTime.now();

        final Expense expense = newExpense()
            .vat(new BigDecimal(20))
            .reason("Bills")
            .date(now)
            .build();

        mockMvc.perform(
                post("/expenses")
                    .content(json(expense))
                    .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    private static String json(final Expense expense) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(expense);
        } catch (final JsonProcessingException e) {
            fail("Failed to convert Expense to json");
            return null;
        }
    }
}

