package dd.task.modulith.spike.shared.events;

public record OrderPlacedEvent(String orderId, String sku, int quantity) {}
