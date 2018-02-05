package com.pqt.core.entities.sale;

import com.pqt.core.entities.log.ILoggable;
import com.pqt.core.entities.members.Client;
import com.pqt.core.entities.product.Product;
import com.pqt.core.entities.user_account.Account;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Notmoo on 18/07/2017.
 */
public class Sale implements ILoggable, Serializable{

    private int id;
    private Map<Product, Integer> products;
    private Map<Product, Boolean> serving;
    private Client orderedWith;
    private Account orderedBy;
    private Account orderedFor;
    private SaleType type;
    private SaleStatus status;

    public Sale() {
    }

    public Sale(int id, Map<Product, Integer> products, Client orderedWith, Account orderedBy, Account orderedFor, SaleType type, SaleStatus status) {
        this.id = id;
        this.orderedWith = orderedWith;
        this.orderedBy = orderedBy;
        this.orderedFor = orderedFor;
        this.type = type;
        this.status = status;
        setProducts(products);
    }

    public Sale(int id, Map<Product, Integer> products, Client orderedWith, Account orderedBy, Account orderedFor, SaleType type, SaleStatus status, Map<Product, Boolean> serving) {
        this.id = id;
        this.orderedWith = orderedWith;
        this.orderedBy = orderedBy;
        this.orderedFor = orderedFor;
        this.type = type;
        this.status = status;
        this.products = products;
        this.serving = serving;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        serving = new HashMap<>();
        this.products = products;
        for (Product prod: this.products.keySet()){
            serving.put(prod, false);
        }
    }

    public boolean isAllServed(){
        for (Product prod: serving.keySet()){
            if (!serving.get(prod)){
                return false;
            }
        }
        return true;
    }

    public Client getOrderedWith() {
        return orderedWith;
    }

    public void setOrderedWith(Client orderedWith) {
        this.orderedWith = orderedWith;
    }

    public Account getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(Account orderedBy) {
        this.orderedBy = orderedBy;
    }

    public Account getOrderedFor() {
        return orderedFor;
    }

    public void setOrderedFor(Account orderedFor) {
        this.orderedFor = orderedFor;
    }

    public SaleType getType() {
        return type;
    }

    public void setType(SaleType type) {
        this.type = type;
    }

    public SaleStatus getStatus() {
        return status;
    }

    public void setStatus(SaleStatus status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, products, orderedBy, orderedFor, orderedWith, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!this.getClass().isInstance(obj))
            return false;

        Sale other = Sale.class.cast(obj);
        return this.id == other.id
                && Objects.equals(this.products, other.products)
                && Objects.equals(this.orderedBy, other.orderedBy)
                && Objects.equals(this.orderedFor, other.orderedFor)
                && Objects.equals(this.orderedWith, other.orderedWith)
                && Objects.equals(this.type, other.type);
    }

    public double getTotalPrice() {
        if(type.getPriceMultiplier()==0)
            return 0;
        return getTotalWorth()*type.getPriceMultiplier();
    }

    public double getTotalWorth(){
        double totalWorth = 0;
        for(Product product : this.products.keySet()){
            totalWorth+=product.getPrice()*(double)this.products.get(product);
        }
        return totalWorth;
    }

    public Map<Product, Boolean> getServing() {
        return serving;
    }
}
