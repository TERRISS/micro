package org.example.userservice;

import org.example.client.UserClient;
import org.example.model.Order;
import org.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserClient userClient;

    public Order createOrder(Order order) {
        if (userClient.isExists(order.getUserId())) {
            return orderRepository.save(order);
        }
        return null;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}