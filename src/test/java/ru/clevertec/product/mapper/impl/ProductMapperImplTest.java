package ru.clevertec.product.mapper.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import util.ProductArgumentsProviderToProduct;

class ProductMapperImplTest {

  private final ProductMapperImpl productMapper = new ProductMapperImpl();

  @ParameterizedTest
  @ArgumentsSource(ProductArgumentsProviderToProduct.class)
  void testToProduct(ProductDto productDto, Product expected) {
    // Given
    // ProductDto and expected Product are provided by the MethodSource

    // When
    Product actual = productMapper.toProduct(productDto);

    // Then
    assertThat(actual)
        .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
        .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
        .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice());
  }

  @Test
  void testToInfoProductDto() {
    assertThat(productMapper.toInfoProductDto(Product.builder().build())).isNull();
  }

  @Test
  void testMerge() {
    assertThat(productMapper.merge(Product.builder().build(), ProductDto.builder().build()))
        .isNull();
  }
}
