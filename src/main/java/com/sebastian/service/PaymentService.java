package com.sebastian.service;

import com.sebastian.model.Order;
import com.sebastian.response.PaymentResponse;
import com.stripe.exception.StripeException;


public interface PaymentService {

    public PaymentResponse createPaymentLink(Order order) throws StripeException;

}
