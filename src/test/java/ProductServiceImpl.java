package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private Stock stock;

    @BeforeEach
    void setUp() {
        stock = new Stock();
        stock.setIdStock(1L);
        product = new Product();
        product.setIdProduct(1L);
        product.setCategory(ProductCategory.ELECTRONICS);
        product.setStock(stock);
    }

    @Test
    void testAddProduct() {
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.addProduct(product, 1L);

        assertNotNull(result);
        assertEquals(stock, result.getStock());
        verify(stockRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testRetrieveProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.retrieveProduct(1L);

        assertNotNull(result);
        assertEquals(product.getIdProduct(), result.getIdProduct());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NullPointerException.class, () -> {
            productService.retrieveProduct(1L);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveAllProduct() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));

        List<Product> products = productService.retreiveAllProduct();

        assertNotNull(products);
        assertEquals(1, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveProductByCategory() {
        when(productRepository.findByCategory(ProductCategory.ELECTRONICS)).thenReturn(Arrays.asList(product));

        List<Product> products = productService.retrieveProductByCategory(ProductCategory.ELECTRONICS);

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(ProductCategory.ELECTRONICS, products.get(0).getCategory());
        verify(productRepository, times(1)).findByCategory(ProductCategory.ELECTRONICS);
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testRetrieveProductStock() {
        when(productRepository.findByStockIdStock(1L)).thenReturn(Arrays.asList(product));

        List<Product> products = productService.retreiveProductStock(1L);

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(stock, products.get(0).getStock());
        verify(productRepository, times(1)).findByStockIdStock(1L);
    }
}
