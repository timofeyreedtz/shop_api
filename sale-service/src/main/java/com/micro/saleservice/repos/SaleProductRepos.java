package com.micro.saleservice.repos;



import com.micro.saleservice.model.Product;
import com.micro.saleservice.model.SalesProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.Optional;

public interface SaleProductRepos extends JpaRepository<SalesProduct,Integer> {
    @Query("SELECT c FROM SalesProduct c WHERE c.product = ?1 and (?2 between c.period_start and c.period_end)")
    Optional<SalesProduct> findByProductAndDate(Product product, Timestamp date);
}
