package active.type;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity extends AbstractEntity {
    private Long id;
    private UUID agencyGuid;
    private UUID orderGuid;
    private String orderNumber;
    private Instant businessDate;
    private BigDecimal amount;
    private LocationEntity locationEntity;
  
}