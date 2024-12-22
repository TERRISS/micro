import org.example.client.UserClient;
import org.example.model.Order;
import org.example.userservice.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private UserClient userClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrder_Success() {
        // Arrange
        Order order = new Order();
        order.setUser Id(1L);
        order.setTotalAmount(100.0);
        when(userClient.isExists(1L)).thenReturn(true); // Пользователь существует
        when(orderService.createOrder(order)).thenReturn(order);

        // Act
        ResponseEntity<?> response = orderController.createOrder(order);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(order, response.getBody());
    }

    @Test
    public void testCreateOrder_UserNotFound() {

        Order order = new Order();
        order.setUser Id(1L);
        when(userClient.isExists(1L)).thenReturn(false); // Пользователь не существует
        ResponseEntity<?> response = orderController.createOrder(order);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Пользователь не найден", response.getBody());
    }

    @Test
    public void testUser ServiceUnavailable() {

        Order order = new Order();
        order.setUser Id(1L);
        when(userClient.isExists(1L)).thenThrow(new RuntimeException("Service Unavailable"));
        ResponseEntity<?> response = orderController.createOrder(order);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Пользователь не найден", response.getBody());
    }

    @Test
    public void testResponseTime() {

        Order order = new Order();
        order.setUser Id(1L);
        when(userClient.isExists(1L)).thenReturn(true);
        when(orderService.createOrder(order)).thenReturn(order);
        long startTime = System.currentTimeMillis();
        orderController.createOrder(order);
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;

        assert(responseTime < 1000);
    }
}