package com.micro.saleservice.controller;

import com.micro.saleservice.error.Message;
import com.micro.saleservice.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaleRestController {

    private SaleService saleService;

    @Autowired
    public SaleRestController(SaleService saleService) {
        this.saleService = saleService;
    }
    @PostMapping(value = "/add")
    public Message addSale(@RequestParam int product_id, @RequestParam int sale, @RequestParam String start, @RequestParam String end){
        return saleService.addSaleForProduct(product_id,sale,start,end);
    }


    @PutMapping(value = "/period")
    public Message updateSalePeriod(@RequestParam int id,
                                    @RequestParam String start,
                                    @RequestParam String end
    ){
        return saleService.updateSalePeriod(id,start,end);
    }


    @PutMapping(value = "/value")
    public Message updateSalePeriod(@RequestParam int id,
                                    @RequestParam int sale
    ){
        return saleService.updateSale(id,sale);
    }
}
