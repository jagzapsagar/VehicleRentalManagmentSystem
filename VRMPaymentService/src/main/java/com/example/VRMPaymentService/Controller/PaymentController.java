package com.example.VRMPaymentService.Controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.VRMPaymentService.dto.PaymentRequest;
import com.example.VRMPaymentService.dto.PaymentResponse;
import com.razorpay.RazorpayClient;
import com.razorpay.Order;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
    private RazorpayClient razorpayClient;

    @PostMapping("/create-order")
    public PaymentResponse createOrder(@RequestBody PaymentRequest request) throws Exception {
        JSONObject options = new JSONObject();
        options.put("amount", request.getAmount());
        options.put("amount", request.getAmount() * 100);
        options.put("currency", request.getCurrency());
        options.put("receipt", request.getReceipt());
        options.put("payment_capture", 1);

        //Order order = razorpayClient.Orders.create(options);
        Order order = razorpayClient.orders.create(options);

        PaymentResponse response = new PaymentResponse();
        response.setId(order.get("id"));
        response.setCurrency(order.get("currency"));
        //response.setAmount(order.get("amount"));
        response.setAmount(Integer.valueOf(order.get("amount").toString()) / 100);
        response.setStatus(order.get("status"));

        return response;
    }

}
