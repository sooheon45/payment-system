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
        value = "/payments/requsetpayment",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Payment requsetPayment(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody RequsetPaymentCommand requsetPaymentCommand
    ) throws Exception {
        System.out.println("##### /payment/requsetPayment  called #####");
        Payment payment = new Payment();
        payment.requsetPayment(requsetPaymentCommand);
        paymentRepository.save(payment);
        return payment;
    }

    @RequestMapping(
        value = "/payments/requestrefund",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Payment requestRefund(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody RequestRefundCommand requestRefundCommand
    ) throws Exception {
        System.out.println("##### /payment/requestRefund  called #####");
        Payment payment = new Payment();
        payment.requestRefund(requestRefundCommand);
        paymentRepository.save(payment);
        return payment;
    }
}
//>>> Clean Arch / Inbound Adaptor
