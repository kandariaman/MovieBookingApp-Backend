package com.example.controller;

import com.example.demo.common.dto.PaymentRequest;
import com.example.service.RazorpayService;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private RazorpayService razorpayService;

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody PaymentRequest paymentRequest) {
        try {
            String order = razorpayService.createOrder(paymentRequest.getAmount(), paymentRequest.getScreeningId());
            return ResponseEntity.ok(order);
        } catch (RazorpayException e) {
            return ResponseEntity.status(500).body("Error creating Razorpay order: " + e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody Map<String, String> request) {

        try{
            boolean isValid = razorpayService.verifyPayment(request);

            if(isValid) {
                return ResponseEntity.ok("Booking confirmed");
            }

            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid signature, Request may be tampered");
            }

        } catch (RazorpayException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment failed verification !!" + e.getMessage());
        }
    }
}
