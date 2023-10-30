package ru.clevertec.product.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductMapper mockMapper;
    @Mock
    private ProductRepository mockProductRepository;

    private ProductServiceImpl productServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        productServiceImplUnderTest = new ProductServiceImpl(mockMapper, mockProductRepository);
    }

    @Test
    void testGet() {
        assertThat(productServiceImplUnderTest.get(UUID.fromString("f02052f4-4c9e-4f09-85db-178f65551547"))).isNull();
    }

    @Test
    void testGetAll() {
        assertThat(productServiceImplUnderTest.getAll()).isNull();
    }

    @Test
    void testCreate() {
        assertThat(productServiceImplUnderTest.create(
                new ProductDto("name", "description", new BigDecimal("0.00")))).isNull();
    }

    @Test
    void testUpdate() {
        productServiceImplUnderTest.update(UUID.fromString("2e7f58f2-d0b4-4314-8b4e-58f014e2be71"),
                new ProductDto("name", "description", new BigDecimal("0.00")));
    }

    @Test
    void testDelete() {
        productServiceImplUnderTest.delete(UUID.fromString("b68f32bc-25de-4c96-b2e4-965761de2b31"));
    }
}
