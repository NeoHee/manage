package com.manage.modular.pojo;
import java.math.BigDecimal;
import java.math.BigInteger;

public class ReportTotal {

	private BigDecimal sumIsbn =new BigDecimal(0);
	
	private BigDecimal sumBook  =new BigDecimal(0);
	
	private Double sumPrice =0.00;
	
	private BigInteger  sumCount;

	public Double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}

	public BigDecimal getSumIsbn() {
		return sumIsbn;
	}

	public void setSumIsbn(BigDecimal sumIsbn) {
		this.sumIsbn = sumIsbn;
	}

	public BigDecimal getSumBook() {
		return sumBook;
	}

	public void setSumBook(BigDecimal sumBook) {
		this.sumBook = sumBook;
	}

	public BigInteger getSumCount() {
		return sumCount;
	}

	public void setSumCount(BigInteger sumCount) {
		this.sumCount = sumCount;
	}
	
}
