package com.example.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RazorpayService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    public String createOrder(int amount, String screeningId) throws RazorpayException {

        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + screeningId);

        Order order = client.orders.create(orderRequest);
        return order.toString();
    }

    public boolean verifyPayment(Map<String, String> request) throws RazorpayException {
        JSONObject requestObj = new JSONObject();

        requestObj.put("razorpay_order_id",request.get("razorpay_order_id"));
        requestObj.put("razorpay_payment_id", request.get("razorpay_payment_id"));
        requestObj.put("razorpay_signature", request.get("razorpay_signature"));

        return Utils.verifyPaymentSignature(requestObj, keySecret);
    }
}
