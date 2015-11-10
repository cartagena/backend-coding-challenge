package com.github.cartagena.alchemytec.expense;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.cartagena.alchemytec.json.ZonedDateTimeJsonDeserializer;
import com.github.cartagena.alchemytec.json.ZonedDateTimeJsonSerializer;

@Entity
@Data
@Builder(builderMethodName = "newExpense")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Expense {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @JsonDeserialize(using = ZonedDateTimeJsonDeserializer.class)
    @JsonSerialize(using = ZonedDateTimeJsonSerializer.class)
    private ZonedDateTime date;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal amount;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal vat;

    @NotNull
    private String reason;

}
