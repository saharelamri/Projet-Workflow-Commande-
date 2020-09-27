package org.distribution.dto;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CommandRequest {
	int idCmd ;
	double price;
	//@CreationTimestamp
	@Temporal(value = TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	Date dtl ;
	 @JsonProperty("collection")
	Collection <ProductDetails> products ;
	 String description;
	//ProductDetails products;
	public int getIdCmd() {
		return idCmd;
	}
	public void setIdCmd(int idCmd) {
		this.idCmd = idCmd;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getDtl() {
		return dtl;
	}
	public void setDtl(Date dtl) {
		this.dtl = dtl;
	}
	
	public Collection<ProductDetails> getProducts() {
		return products;
	}
	public void setProducts(Collection<ProductDetails> products) {
		this.products = products;
	}

	public CommandRequest(int idCmd, double price, Date dtl, Collection<ProductDetails> products, String description) {
		super();
		this.idCmd = idCmd;
		this.price = price;
		this.dtl = dtl;
		this.products = products;
		this.description = description;
	}

	@Override
	public String toString() {
		return "CommandRequest [idCmd=" + idCmd + ", price=" + price + ", dtl=" + dtl + ", products=" + products
				+ ", description=" + description + "]";
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
