package org.koreait.admin.product.repogitories;

import org.koreait.admin.product.entities.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductRepository extends ListCrudRepository<Product, Long> {
}
