package dd.task.modulith.spike.order.adapter.in.web;

import dd.task.modulith.spike.order.adapter.in.web.model.PlaceOrderRequest;
import dd.task.modulith.spike.order.application.OrdersApplicationService;
import dd.task.modulith.spike.order.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
class OrdersController {

    private final OrdersApplicationService ordersApplicationService;

    @PostMapping
    Order place(@RequestBody PlaceOrderRequest request) {
        return ordersApplicationService.place(request.sku(), request.quantity());
    }

    @GetMapping("/{id}")
    ResponseEntity<Order> find(@PathVariable UUID id) {
        return ordersApplicationService.find(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
