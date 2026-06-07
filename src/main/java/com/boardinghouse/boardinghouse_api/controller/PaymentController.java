package com.boardinghouse.boardinghouse_api.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.midtrans.service.MidtransCoreApi;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private MidtransCoreApi midtransCoreApi;

    @PostMapping("/charge")
    public ResponseEntity<Map<String, Object>> charge(@RequestBody Map<String, Object> request){
        Map<String, Object> response = new HashMap<>();

        try{
            String orderId = "ORDER-" + UUID.randomUUID().toString().substring(0, 8);
            Number amount = (Number) request.getOrDefault("amount", 100000);

            Map<String, Object> params = new HashMap<>();
            Map<String, Object> transactionDetails = new HashMap<>();
            transactionDetails.put("order_id", orderId);
            transactionDetails.put("gross_amount", amount);
            params.put("transaction_details", transactionDetails);
            params.put("payment_type", "bank_transfer");

            Map<String, Object> midtransResponse = midtransCoreApi.chargeTransaction(params).toMap();

            response.put("status", "SUCCESS");
            response.put("orderId", orderId);
            response.put("midtransResponse", midtransResponse);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("status", "ERROR");
            response.put("message", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
