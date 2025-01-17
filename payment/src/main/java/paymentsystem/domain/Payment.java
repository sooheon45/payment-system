package paymentsystem.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import paymentsystem.PaymentApplication;
import paymentsystem.domain.PaymentCompleted;
import paymentsystem.domain.PaymentRefunded;

@Entity
@Table(name = "Payment_table")
@Data
//<<< DDD / Aggregate Root
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String paymethod;

    private String storeId;

    private Integer price;

    private Date timeStamp;

    private String name;

    private String buyerName;

    private String buyerTel;

    private String buyerEmail;

    @PostPersist
    public void onPostPersist() {
        PaymentCompleted paymentCompleted = new PaymentCompleted(this);
        paymentCompleted.publishAfterCommit();

        PaymentRefunded paymentRefunded = new PaymentRefunded(this);
        paymentRefunded.publishAfterCommit();
    }

    public static PaymentRepository repository() {
        PaymentRepository paymentRepository = PaymentApplication.applicationContext.getBean(
            PaymentRepository.class
        );
        return paymentRepository;
    }

    //<<< Clean Arch / Port Method
    public void requsetPayment(RequsetPaymentCommand requsetPaymentCommand) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void requestRefund(RequestRefundCommand requestRefundCommand) {
        //implement business logic here:

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
