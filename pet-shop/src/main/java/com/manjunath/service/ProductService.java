package com.manjunath.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.manjunath.Exception.ProductException;
import com.manjunath.modal.Product;
import com.manjunath.modal.Seller;
import com.manjunath.request.CreateProductRequest;

public interface ProductService {

	public Product createproduct(CreateProductRequest req, Seller seller);

	public void deleteProduct(Long id) throws ProductException;

	public Product updateProduct(Long id, Product product) throws ProductException;

	public Product findProductById(Long id) throws ProductException;

	List<Product> searchProduct();

	public Page<Product> getAllProduct(String Category, String brand, String colors, String sizes, Integer minPrice,
			Integer maxPrice, String minDiscount, String sort, String stock, Integer pageNumber);

	List<Product> getProductBySellerId(Long sellerId);

}
