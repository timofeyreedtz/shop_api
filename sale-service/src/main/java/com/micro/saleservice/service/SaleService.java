package com.micro.saleservice.service;

import com.micro.saleservice.error.Message;
import com.micro.saleservice.model.Product;
import com.micro.saleservice.model.Sale;
import com.micro.saleservice.model.SalesProduct;
import com.micro.saleservice.repos.ProductRepos;
import com.micro.saleservice.repos.SaleProductRepos;
import com.micro.saleservice.repos.SaleRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class SaleService {
    private ProductRepos productRepos;
    private SaleProductRepos saleProductRepos;
    private SaleRepos saleRepos;

    @Autowired
    public SaleService(ProductRepos productRepos, SaleProductRepos saleProductRepos, SaleRepos saleRepos) {
        this.productRepos = productRepos;
        this.saleProductRepos = saleProductRepos;
        this.saleRepos = saleRepos;
    }

    public Message addSaleForProduct(int product_id, int sale, String start, String end) {
        Product product = productRepos.findById(product_id).orElseThrow(()->new IllegalStateException("There is no such product"));
        if(sale >= 100){
            throw new IllegalStateException("Sale can't be more than 100%");
        }
        Sale saleObject = new Sale();
        saleObject.setSale_in_percent(sale);
        saleRepos.save(saleObject);
        SalesProduct salesProduct = new SalesProduct();
        salesProduct.setId(1);
        salesProduct.setProduct(product);
        salesProduct.setSale(saleObject);
        salesProduct.setPeriod_start(Timestamp.valueOf(start));
        salesProduct.setPeriod_end(Timestamp.valueOf(end));
        saleProductRepos.save(salesProduct);
        return new Message(true);
    }

    public Message updateSalePeriod(int sale_product_id, String start, String end) {
        SalesProduct salesProduct = saleProductRepos.findById(sale_product_id).orElseThrow(()->new IllegalStateException("There is no such sale"));
        salesProduct.setPeriod_start(Timestamp.valueOf(start));
        salesProduct.setPeriod_end(Timestamp.valueOf(end));
        saleProductRepos.save(salesProduct);
        return new Message(true);
    }

    public Message updateSale(int sale_product_id, int sale) {
        if(sale >= 100){
            throw new IllegalStateException("Sale can't be more than 100%");
        }
        SalesProduct salesProduct = saleProductRepos.findById(sale_product_id).orElseThrow(()->new IllegalStateException("There is no such sale"));
        salesProduct.getSale().setSale_in_percent(sale);
        saleProductRepos.save(salesProduct);
        return new Message(true);
    }
}
