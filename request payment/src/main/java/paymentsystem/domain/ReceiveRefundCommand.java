package paymentsystem.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class ReceiveRefundCommand {

    private String amount;
}
