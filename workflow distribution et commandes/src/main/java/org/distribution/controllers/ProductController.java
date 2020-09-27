package org.distribution.controllers;

import java.util.Collection;

import org.distribution.dto.ProductDetails;
import org.distribution.metier.ProductInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Product")
@CrossOrigin("*")
public class ProductController {

	@Autowired
	private ProductInterface PF;
	@PostMapping("/add_pdt")
	public void save(@RequestBody ProductDetails pdt) {
		PF.Add(pdt);
	}
	
	@GetMapping("/all_pdt")
	public Collection<ProductDetails> findAll(){
		return PF.getAll();
		
	}
	@GetMapping("/get_pdt/{id}")
	ProductDetails affiche(@PathVariable long CodePdt) {
		return PF.getId(CodePdt);
		
	}
	@DeleteMapping("/delete_pdt/{id}")
	public void delete_lot(@PathVariable long CodePdt) {
		PF.Delete(CodePdt);
	}
	@PutMapping("/edit_pdt/{id}")
	public void update(@PathVariable Long CodePdt, @RequestBody ProductDetails pdt)
	{
		pdt.setIdPdt(CodePdt);
		PF.Add(pdt);
		
	}
}
