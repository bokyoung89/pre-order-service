package com.bokyoung.productService.application.service;

import com.bokyoung.productService.infra.feign.StockFeignClient;
import com.bokyoung.productService.global.exception.ErrorCode;
import com.bokyoung.productService.global.exception.PreOrderServiceException;
import com.bokyoung.productService.domain.model.Product;
import com.bokyoung.productService.domain.model.ProductType;
import com.bokyoung.productService.dao.repository.ProductRepository;
import com.bokyoung.productService.domain.entity.ProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StockFeignClient stockFeignClient;

    @Transactional
    public void create(String name, Long userId, String content, int price, ProductType productType, Integer stocksCount) {
        //상품 등록
        ProductEntity productEntity = productRepository.save(ProductEntity.of(name, userId, content, price, productType));

        //재고 등록
        stockFeignClient.createStock(productEntity.getId(), productType, stocksCount);
    }

    @Transactional
    public Product modify(String name, String content, int price, ProductType productType, Integer stockCount, Long productId, Long userId) {
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

        if (productType != null) {
            productEntity.setProductType(productType);
        }

        Product product = Product.fromEntity(productRepository.save(productEntity));
        stockFeignClient.modifyStock(productId, stockCount);

        return product;
    }

    @Transactional
    public void delete(Long userId, Long productId) {
        ProductEntity productEntity = getProductEntityOrException(productId);

        if (productEntity.getUserId() != userId) {
            throw new PreOrderServiceException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userId, productId));
        }

        productRepository.delete(productEntity);
        stockFeignClient.deleteStock(productId);
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
