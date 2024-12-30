package paymentsystem.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import paymentsystem.domain.*;
import paymentsystem.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PaymentRefunded extends AbstractEvent {

    private Long id;
    private Integer amount;
    private Date issueddate;
    private Date approvaldate;
    private Date refuneddate;

    public PaymentRefunded(Payment aggregate) {
        super(aggregate);
    }

    public PaymentRefunded() {
        super();
    }
}
//>>> DDD / Domain Event
