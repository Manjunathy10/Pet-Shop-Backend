
package com.manjunath.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.manjunath.Exception.ProductException;
import com.manjunath.modal.Category;
import com.manjunath.modal.Product;
import com.manjunath.modal.Seller;
import com.manjunath.repository.CategoryRepository;
import com.manjunath.repository.ProductRepository;
import com.manjunath.request.CreateProductRequest;
import com.manjunath.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductException productException;

	@Override
	public Product createproduct(CreateProductRequest req, Seller seller) {

		Category category1 = categoryRepository.findByCategoryId(req.getCategory1());

		if (category1 == null) {
			Category category = new Category();
			category.setCategoryId(req.getCategory1());
			category.setLevel(1);
			category1 = categoryRepository.save(category);
		}

		Category category2 = categoryRepository.findByCategoryId(req.getCategory1());

		if (category2 == null) {
			Category category = new Category();
			category.setCategoryId(req.getCategory2());
			category.setParentCategory(category1);
			category.setLevel(2);
			category2 = categoryRepository.save(category);

		}

		Category category3 = categoryRepository.findByCategoryId(req.getCategory3());

		if (category3 == null) {

			Category category = new Category();

			category.setCategoryId(req.getCategory3());
			category.setParentCategory(category2);
			category.setLevel(3);
			category3 = categoryRepository.save(category);

		}

		int discountPercentage = calculateDiscountPercentge(req.getMrpPrice(), req.getSelllingPrice());

		Product product = new Product();

		product.setSeller(seller);
		product.setCategory(category3);
		product.setDescription(req.getDescription());
		product.setCreatedAt(LocalDateTime.now());
		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setSellingPrice(req.getSelllingPrice());
		product.setImages(req.getImages());
		product.setMrpPrice(req.getMrpPrice());
		product.setSizes(req.getSizes());
		product.setDiscountPercentage(discountPercentage);

		return productRepository.save(product);
	}

	private int calculateDiscountPercentge(int mrpPrice, int selllingPrice) {

		if (mrpPrice <= 0) {
			throw new IllegalArgumentException("Actual price must be greater than 0");
		}

		double discount = mrpPrice - selllingPrice;
		double discountPercentage = (discount / mrpPrice) * 100;
		return (int) discountPercentage;
	}

	@Override
	public void deleteProduct(Long id) throws ProductException {

		Product product = productRepository.findById(id).orElseThrow(() -> new ProductException("product not found"));

		productRepository.delete(product);

	}

	@Override
	public Product updateProduct(Long id, Product product) throws ProductException {
		findProductById(id);
		
		product.setId(id);
		
		return productRepository.save(product);
	}

	@Override
	public Product findProductById(Long id) throws ProductException {
		Product product= productRepository.findById(id).orElseThrow(()->
		new ProductException("product not found with the id "+id)
				);
		return product;
	}

	@Override
	public List<Product> searchProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String Category, String brand, String colors, String sizes, Integer minPrice,
			Integer maxPrice, String minDiscount, String sort, String stock, Integer pageNumber) {
		
	
//		Specification<Product> spec=(root,query,criteriaBuilder)->{
//			List <P redicate>
//		}
		return null;
	}

	@Override
	public List<Product> getProductBySellerId(Long sellerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
