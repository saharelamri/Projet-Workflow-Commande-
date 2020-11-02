package org.distribution.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "Products" )
public class ProductDetails {
	@Id
     private Long idPdt ;
	 private String LibellePdt ;
	private double price ;
	private String unite;
	private long qte;
	
	public String getUnite() {
		return unite;
	}
	public void setUnite(String unite) {
		this.unite = unite;
	}
	public long getQte() {
		return qte;
	}
	public void setQte(long qte) {
		this.qte = qte;
	}
	public Long getIdPdt() {
		return idPdt;
	}
	public void setIdPdt(Long codePdt) {
		this.idPdt = codePdt;
	}
	public String getLibellePdt() {
		return LibellePdt;
	}
	public void setLibellePdt(String libellePdt) {
		LibellePdt = libellePdt;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public ProductDetails(Long idPdt, String libellePdt, double price, String unite, long qte) {
		super();
		this.idPdt = idPdt;
		LibellePdt = libellePdt;
		this.price = price;
		this.unite = unite;
		this.qte = qte;
	}
	public ProductDetails() {
		super();
	}
	@Override
	public String toString() {
		return "ProductDetails [idPdt=" + idPdt + ", LibellePdt=" + LibellePdt + ", price=" + price + "]";
	} 
	
	

}
