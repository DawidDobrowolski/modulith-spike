package dd.task.modulith.spike.shared.events;

import java.util.UUID;

public record OrderPlacedEvent(UUID orderId, String sku, int quantity) {}
