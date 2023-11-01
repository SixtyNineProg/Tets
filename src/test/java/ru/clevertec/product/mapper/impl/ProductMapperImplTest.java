package ru.clevertec.product.mapper.impl;

import org.junit.jupiter.api.Test;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductMapperImplTest {

  private ProductMapperImpl productMapper;

  private static Stream<Arguments> provideEmailArguments() {
    return Stream.of(
        Arguments.of(
            "Kuzmich Vladislav <v.kuzmich@invento-labs.com>", "v.kuzmich@invento-labs.com"),
        Arguments.of("r.rantsevich@atevisystems.com", "r.rantsevich@atevisystems.com"));
  }

  @ParameterizedTest
  @MethodSource("provideEmailArguments")
  void testGetContent_ValidStringWithEmail_EmailExtracted(String input, String expectedEmail) {
    // Given
    // input and expectedEmail are provided by the MethodSource

    // When
    Optional<String> extractedEmail = ParseUtils.getContent(input, REGEX_EMAIL_FROM_ADDRESS);

    // Then
    assertTrue(extractedEmail.isPresent(), "Email should be extracted");
    assertEquals(
        expectedEmail, extractedEmail.get(), "Extracted email should match expected email");
  }

  private static Stream<Arguments> provideLoginArguments() {
    return Stream.of(
        Arguments.of("[Reset][L.Sjoberg]", "L.Sjoberg"),
        Arguments.of("[Reset][L.Sjoberg] >", "L.Sjoberg"));
  }

  @ParameterizedTest
  @MethodSource("provideLoginArguments")
  void testGetContent_ValidStringWithLogin_LoginExtracted(String input, String expectedLogin) {
    // Given
    // input and expectedLogin are provided by the MethodSource

    // When
    Optional<String> extractedLogin =
        ParseUtils.getContent(input, REGEX_GET_LOGIN, NUM_GROUP_GET_LOGIN);

    // Then
    assertTrue(extractedLogin.isPresent(), "Login should be extracted");
    assertEquals(
        expectedLogin, extractedLogin.get(), "Extracted login should match expected login");
  }

  private static Stream<Arguments> provideEmailArguments() {
    return Stream.of(
        Arguments.of(
            "Kuzmich Vladislav <v.kuzmich@invento-labs.com>", "v.kuzmich@invento-labs.com"),
        Arguments.of("r.rantsevich@atevisystems.com", "r.rantsevich@atevisystems.com"));
  }

  @Test
  void testToProduct() {
    assertThat(productMapper.toProduct(ProductDto.builder().build())).isNull();
  }

  @Test
  void testToInfoProductDto() {
    assertThat(productMapper.toInfoProductDto(Product.builder().build())).isNull();
  }

  @Test
  void testToListInfoProductDto() {
    assertThat(productMapper.toListInfoProductDto(List.of(Product.builder().build()))).isNull();
  }

  @Test
  void testMerge() {
    assertThat(productMapper.merge(Product.builder().build(), ProductDto.builder().build()))
        .isNull();
  }
}
