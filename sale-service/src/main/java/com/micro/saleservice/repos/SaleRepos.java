package com.micro.saleservice.repos;



import com.micro.saleservice.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SaleRepos extends JpaRepository<Sale,Integer> {

    @Query("SELECT c FROM Sale c WHERE c.sale_in_percent = ?1")
    Optional<Sale> findBySale_in_percent(Integer sale);
}
