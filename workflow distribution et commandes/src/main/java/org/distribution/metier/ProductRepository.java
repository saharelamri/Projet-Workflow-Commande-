package org.distribution.metier;

import java.util.List;

import org.distribution.dto.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductDetails,Long> {
	//List<ProductDetails> findByLibellePdt(String lbl);
	

}
