package paymentsystem.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class ReceiveCancelledCompletedCommand {

    private Long id;
    private Long itemId;
    private String paymentId;
    private String status;
    private String reason;
}
