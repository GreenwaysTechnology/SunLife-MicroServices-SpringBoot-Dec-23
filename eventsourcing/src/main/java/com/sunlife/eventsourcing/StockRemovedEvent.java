package com.sunlife.eventsourcing;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StockRemovedEvent implements StockEvent {
    private Stock stockDetails;
}
