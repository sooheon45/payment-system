package paymentsystem.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import paymentsystem.domain.*;
import paymentsystem.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PaymentApproved extends AbstractEvent {

    private Long id;
    private Integer amount;
    private Date issueddate;
    private Date approvaldate;

    public PaymentApproved(Payment aggregate) {
        super(aggregate);
    }

    public PaymentApproved() {
        super();
    }
}
//>>> DDD / Domain Event
