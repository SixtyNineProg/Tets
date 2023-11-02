package ru.clevertec.product.data;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

@Builder
@FieldNameConstants
public record ProductDto(

    /** {@link ru.clevertec.product.entity.Product} */
    String name,

    /** {@link ru.clevertec.product.entity.Product} */
    String description,

    /** {@link ru.clevertec.product.entity.Product} */
    BigDecimal price) {}
