package com.healthcare.patient.controller;

import com.healthcare.patient.common.ApiResponse;
import com.healthcare.patient.dto.CreateTestOrderRequest;
import com.healthcare.patient.dto.TestOrderResponseDTO;
import com.healthcare.patient.dto.item.*;
import com.healthcare.patient.service.TestOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-orders")
@RequiredArgsConstructor
public class TestOrderController {

    private final TestOrderService service;

    @GetMapping
    public ApiResponse<List<TestOrderResponseDTO>> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public ApiResponse<TestOrderResponseDTO> getById(@PathVariable Long id) { return service.getById(id); }

    @PostMapping
    public ApiResponse<TestOrderResponseDTO> create(@Valid @RequestBody CreateTestOrderRequest req) {
        return service.create(req);
    }

    @PostMapping("/{id}/complete")
    public ApiResponse<TestOrderResponseDTO> complete(@PathVariable Long id, @RequestParam String runBy) {
        return service.markCompleted(id, runBy);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id, @RequestParam String deletedBy) {
        return service.delete(id, deletedBy);
    }

    // === Items ===
    @GetMapping("/{orderId}/items")
    public ApiResponse<List<TestOrderItemResponse>> listItems(@PathVariable Long orderId) {
        return service.listItems(orderId);
    }

    @PostMapping("/{orderId}/items")
    public ApiResponse<TestOrderItemResponse> addItem(@PathVariable Long orderId,
                                                      @Valid @RequestBody CreateTestOrderItemRequest dto) {
        return service.addItem(orderId, dto);
    }

    @PutMapping("/{orderId}/items/{itemId}")
    public ApiResponse<TestOrderItemResponse> updateItem(@PathVariable Long orderId,
                                                         @PathVariable Long itemId,
                                                         @RequestBody UpdateTestOrderItemRequest dto) {
        return service.updateItem(orderId, itemId, dto);
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public ApiResponse<Void> deleteItem(@PathVariable Long orderId, @PathVariable Long itemId) {
        return service.deleteItem(orderId, itemId);
    }
}
