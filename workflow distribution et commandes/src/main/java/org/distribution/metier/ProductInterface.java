package org.distribution.metier;

import java.util.Collection;

import org.distribution.dto.ProductDetails;

public interface ProductInterface {
	public Collection <ProductDetails> getAll();
	public ProductDetails getId(long id);
	public void Delete(long id);
	public ProductDetails Add(ProductDetails l);
	//Collection<ProductDetails> findByLibellePdt(String lbl);

}
