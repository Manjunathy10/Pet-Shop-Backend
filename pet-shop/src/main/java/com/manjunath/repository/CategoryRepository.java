package com.manjunath.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manjunath.modal.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByCategoryId(String categoryId);
}
