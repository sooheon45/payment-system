package paymentsystem.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paymentsystem.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/payments")
@Transactional
public class PaymentController {

    @Autowired
    PaymentRepository paymentRepository;

    @RequestMapping(
        value = "/payments/receivepayment",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Payment receivePayment(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody ReceivePaymentCommand receivePaymentCommand
    ) throws Exception {
        System.out.println("##### /payment/receivePayment  called #####");
        Payment payment = new Payment();
        payment.receivePayment(receivePaymentCommand);
        paymentRepository.save(payment);
        return payment;
    }

    @RequestMapping(
        value = "/payments/receiverefund",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Payment receiveRefund(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody ReceiveRefundCommand receiveRefundCommand
    ) throws Exception {
        System.out.println("##### /payment/receiveRefund  called #####");
        Payment payment = new Payment();
        payment.receiveRefund(receiveRefundCommand);
        paymentRepository.save(payment);
        return payment;
    }
}
//>>> Clean Arch / Inbound Adaptor
