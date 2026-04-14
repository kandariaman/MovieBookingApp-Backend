package com.example.service;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RazorpayServiceTest {

    @Test
    void testRazorpayOrderPayload() {

        // Arrange
        Integer amount = 500;
        Long screeningId = 10L;

        //Act
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + screeningId);

        // Output
        System.out.println("Generated JSON Payload:");
        System.out.println(orderRequest.toString(4));

        // 4. Assert (Verification)
        assertEquals(50000, orderRequest.getInt("amount"), "Amount should be in paise");
        assertEquals("INR", orderRequest.getString("currency"));
        assertEquals("txn_101", orderRequest.getString("receipt"));
    }
}