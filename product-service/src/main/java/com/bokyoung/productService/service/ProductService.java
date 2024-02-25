package com.bokyoung.productService.service;

import com.bokyoung.productService.exception.ErrorCode;
import com.bokyoung.productService.exception.PreOrderServiceException;
import com.bokyoung.productService.model.Product;
import com.bokyoung.productService.model.ProductType;
import com.bokyoung.productService.model.entity.ProductStockEntity;
import com.bokyoung.productService.repository.ProductRepository;
import com.bokyoung.productService.model.entity.ProductEntity;
import com.bokyoung.productService.repository.ProductStockRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductStockRepository productStockRepository;

    @Transactional
    public void create(String name, Long userId, String content, int price, ProductType productType, Integer stocksCount) {
        //상품 등록
        ProductEntity productEntity = productRepository.save(ProductEntity.of(name, userId, content, price, productType));

        //재고는 0보다 커야 한다.
        ProductStockEntity productStockEntity = ProductStockEntity.of(productEntity.getId(), productType, stocksCount);
        if(productStockEntity.getStockCount() == 0) {
            throw new PreOrderServiceException(ErrorCode.STOCK_COUNT_IS_ZERO, String.format("StockCount is zero"));
        }
        productStockRepository.save(productStockEntity);
    }

    @Transactional
    public Product modify(String name, String content, int price, ProductType productType, Long userId, Long productId) {
        ProductEntity productEntity = getProductEntityOrException(productId);

        if (productEntity.getUserId() != userId) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userId, productId));
        }

        if (name != null) {
            productEntity.setName(name);
        }
        if (content != null) {
            productEntity.setContent(content);
        }
            productEntity.setPrice(price);

        if (productType != null) {
            productEntity.setProductType(productType);
        }

        return Product.fromEntity(productRepository.save(productEntity));
    }

    @Transactional
    @CacheEvict(value = "productStock", key = "#productId")
    public void delete(Long userId, Long productId) {
        ProductEntity productEntity = getProductEntityOrException(productId);
        ProductStockEntity productStockEntity = getProductStockEntityOrException(productId);

        if (productEntity.getUserId() != userId) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userId, productId));
        }

        productRepository.delete(productEntity);
        productStockRepository.delete(productStockEntity);
    }

    public Page<Product> list(Pageable pageable) {
        return productRepository.findAll(pageable).map(Product::fromEntity);
    }

    public Product details(Long productId) {
        ProductEntity productEntity = getProductEntityOrException (productId);
        return Product.fromEntity(productEntity);
    }

    // product exist
    private ProductEntity getProductEntityOrException(Long productId) {
        return productRepository.findById(productId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.PRODUCT_NOT_FOUND, String.format("%s not founded", productId)));
    }

    // stock exist
    private ProductStockEntity getProductStockEntityOrException(Long productId) {
        return productStockRepository.findById(productId).orElseThrow(() ->
                new PreOrderServiceException(ErrorCode.STOCK_NOT_FOUND, String.format("%s not founded", productId)));
    }
}
