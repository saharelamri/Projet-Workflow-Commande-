package org.distribution.metier;

import java.util.Collection;

import org.distribution.dto.ProductDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements ProductInterface {
	@Autowired
	ProductRepository PR;
	

	@Override
	public Collection<ProductDetails> getAll() {
		// TODO Auto-generated method stub
		return PR.findAll();
	}

	@Override
	public ProductDetails getId(long id) {
		// TODO Auto-generated method stub
	 	return PR.findById(id).get();

	}

	@Override
	public void Delete(long id) {
		// TODO Auto-generated method stub
		PR.deleteById(id);
	}

	@Override
	public ProductDetails Add(ProductDetails l) {
		// TODO Auto-generated method stub
		return PR.save(l);
	}
	/*@Override
	public Collection<ProductDetails> findByLibellePdt(String lbl) {
		// TODO Auto-generated method stub
		return PR.findByLibellePdt(lbl);
	}
	*/

}
