package ru.clevertec.product.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.util.ProductTestData;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Mock private ProductMapper mapper;
  @Mock private ProductRepository productRepository;

  @Captor private ArgumentCaptor<UUID> uuidArgumentCaptor;

  @InjectMocks private ProductServiceImpl productServiceImpl;

  @Test
  void testGet_whenGetByUuid_thanProductExpected() {
    // given
    ProductTestData expected = setUpGet();

    // when
    InfoProductDto actual = productServiceImpl.get(expected.getUuid());

    // then
    assertThat(actual)
        .hasFieldOrPropertyWithValue(Product.Fields.uuid, expected.getUuid())
        .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
        .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated())
        .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
        .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription());
  }

  @Test
  void testGet_whenGetByUuid_thanCallOneTimeRepositoryFindByExpected() {
    // given
    ProductTestData expected = setUpGet();

    // when
    productServiceImpl.get(expected.getUuid());

    // then
    verify(productRepository).findById(expected.getUuid());
  }

  @Test
  void testGet_whenGetByUnknownUuid_thanProductNotFoundExceptionExpected() {
    // given
    UUID uuid = UUID.fromString("197ceff8-27a8-4b31-a019-5069ea80ab5b");
    Mockito.doReturn(Optional.empty()).when(productRepository).findById(uuid);

    // when
    ProductNotFoundException thrown =
        Assertions.assertThrows(ProductNotFoundException.class, () -> productServiceImpl.get(uuid));

    // then
    assertThat(thrown).hasMessage(String.format("Product with uuid: %s not found", uuid));
  }

  @Test
  void testGet_whenGetByUuid_thanCallRepositoryWithThisUuid() {
    // given
    ProductTestData expected = setUpGet();

    // when
    productServiceImpl.get(expected.getUuid());

    // then
    verify(productRepository).findById(uuidArgumentCaptor.capture());
    assertThat(uuidArgumentCaptor.getValue()).isEqualByComparingTo(expected.getUuid());
  }

  @Test
  void testGetAll_whenGetAll_thanGetNonEmptyList() {
    // given
    setUpGetAll();

    // when
    List<InfoProductDto> actual = productServiceImpl.getAll();

    // then
    assertThat(actual).isNotEmpty();
  }

  @Test
  void testGetAll_whenGetByUuid_thanCallOneTimeRepositoryFindAllExpected() {
    // given
    setUpGetAll();

    // when
    productServiceImpl.getAll();

    // then
    verify(productRepository).findAll();
  }

  @Test
  void testCreate() {
    assertThat(
            productServiceImpl.create(
                new ProductDto("name", "description", new BigDecimal("0.00"))))
        .isNull();
  }

  @Test
  void testUpdate() {
    productServiceImpl.update(
        UUID.fromString("2e7f58f2-d0b4-4314-8b4e-58f014e2be71"),
        new ProductDto("name", "description", new BigDecimal("0.00")));
  }

  @Test
  void testDelete() {
    productServiceImpl.delete(UUID.fromString("b68f32bc-25de-4c96-b2e4-965761de2b31"));
  }

  private ProductTestData setUpGet() {
    ProductTestData expected = ProductTestData.builder().build();
    Product productRepositoryTestData = ProductTestData.builder().build().buildProduct();
    InfoProductDto productMapperTestData = ProductTestData.builder().build().buildInfoProductDto();
    initMocksForGet(productRepositoryTestData, expected, productMapperTestData);
    return expected;
  }

  private void setUpGetAll() {
    List<InfoProductDto> listInfoProductDto =
        ProductTestData.builder().build().buildListInfoProductDto();
    List<Product> productRepositoryTestData = ProductTestData.builder().build().buildListProducts();
    initMocksForGetAll(productRepositoryTestData, listInfoProductDto);
  }

  private void initMocksForGet(
      Product productRepositoryTestData,
      ProductTestData expected,
      InfoProductDto productMapperTestData) {
    Optional<Product> optionalProductRepository = Optional.ofNullable(productRepositoryTestData);
    Mockito.doReturn(optionalProductRepository)
        .when(productRepository)
        .findById(expected.getUuid());
    Mockito.doReturn(productMapperTestData)
        .when(mapper)
        .toInfoProductDto(optionalProductRepository.orElseThrow());
  }

  private void initMocksForGetAll(
          List<Product> productRepositoryTestData, List<InfoProductDto> listInfoProductDto) {
    Mockito.doReturn(productRepositoryTestData).when(productRepository).findAll();
    Mockito.doReturn(listInfoProductDto)
            .when(mapper)
            .toListInfoProductDto(productRepositoryTestData);
  }
}
