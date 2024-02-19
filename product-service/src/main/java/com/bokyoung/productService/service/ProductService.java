package com.bokyoung.productService.service;

import com.bokyoung.productService.exception.ErrorCode;
import com.bokyoung.productService.exception.PreOrderServiceException;
import com.bokyoung.productService.model.Product;
import com.bokyoung.productService.model.ProductType;
import com.bokyoung.productService.repository.ProductRepository;
import com.bokyoung.productService.model.entity.ProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void create(String name, Long userId, String content, int price, ProductType productType) {
        productRepository.save(ProductEntity.of(name, userId, content, price, productType));
    }

    @Transactional
    public Product modify(String name, String content, int price, Long userId, Long productId) {
        ProductEntity productEntity = getProductEntityOrException(productId);

        if (productEntity.getUserId() != userId) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userId, productId));
        }

        productEntity.setName(name);
        productEntity.setContent(content);
        productEntity.setPrice(price);

        return Product.fromEntity(productRepository.save(productEntity));
    }

    @Transactional
    public void delete(Long userId, Long productId) {
        ProductEntity productEntity = getProductEntityOrException(productId);

        if (productEntity.getUserId() != userId) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userId, productId));
        }

        productRepository.delete(productEntity);
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
}
