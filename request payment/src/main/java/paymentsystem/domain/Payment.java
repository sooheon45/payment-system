package paymentsystem.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import paymentsystem.RequestPaymentApplication;
import paymentsystem.domain.PaymentApproved;
import paymentsystem.domain.PaymentRefunded;

@Entity
@Table(name = "Payment_table")
@Data
//<<< DDD / Aggregate Root
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer amount;

    private Date issuedDate;

    private Date approvalDate;

    private Date refunedDate;

    @PostPersist
    public void onPostPersist() {
        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        paymentsystem.external.Store store = new paymentsystem.external.Store();
        // mappings goes here
        RequestPaymentApplication.applicationContext
            .getBean(paymentsystem.external.StoreService.class)
            .payment(store);

        PaymentApproved paymentApproved = new PaymentApproved(this);
        paymentApproved.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        paymentsystem.external.Store store = new paymentsystem.external.Store();
        // mappings goes here
        RequestPaymentApplication.applicationContext
            .getBean(paymentsystem.external.StoreService.class)
            .refund(store);

        PaymentRefunded paymentRefunded = new PaymentRefunded(this);
        paymentRefunded.publishAfterCommit();
    }

    public static PaymentRepository repository() {
        PaymentRepository paymentRepository = RequestPaymentApplication.applicationContext.getBean(
            PaymentRepository.class
        );
        return paymentRepository;
    }

    //<<< Clean Arch / Port Method
    public void receivePayment(ReceivePaymentCommand receivePaymentCommand) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void receiveRefund(ReceiveRefundCommand receiveRefundCommand) {
        //implement business logic here:

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
