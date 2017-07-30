package com.pqt.server.module.stock;

import com.pqt.core.entities.product.Product;
import com.pqt.server.tools.entities.SaleContent;

import java.util.List;
import java.util.Map;

//TODO écrire javadoc
public interface IStockDao {

	public List<Product> getProductList();

	public Product getProduct(long id);

	public void addProduct(Product product);

	public void removeProduct(long id);

	public void modifyProduct(long id, Product product);

	void applySale(SaleContent productAmounts) throws IllegalArgumentException;
}
