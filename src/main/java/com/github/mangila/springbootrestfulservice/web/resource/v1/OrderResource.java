package com.github.mangila.springbootrestfulservice.web.resource.v1;

import com.github.mangila.springbootrestfulservice.web.dto.v1.OrderDto;
import com.github.mangila.springbootrestfulservice.web.service.v1.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("v1/order")
@RestController
public class OrderResource {

    private final OrderService service;

    public OrderResource(OrderService service) {
        this.service = service;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderDto>> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> findById(@PathVariable String id) {
        final OrderDto o = this.service.findById(id);
        return ResponseEntity.ok(o);
    }

    @PostMapping(value = "{customerId}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@PathVariable String customerId,
                                    @Valid @RequestBody OrderDto orderDto,
                                    HttpServletRequest request) {
        final String orderId = this.service.insert(customerId, orderDto);
        final URI location = URI.create(request.getRequestURI()).resolve(orderId);
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
