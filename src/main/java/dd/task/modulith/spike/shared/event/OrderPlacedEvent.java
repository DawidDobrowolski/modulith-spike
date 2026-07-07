package dd.task.modulith.spike.shared.event;

import java.util.UUID;

public record OrderPlacedEvent(UUID orderId, String sku, int quantity) {}
