package paymentsystem.external;

import java.util.Date;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "credit cards", url = "${api.url.credit cards}")
public interface CreditCompanyService {
    @RequestMapping(method = RequestMethod.POST, path = "/creditCompanies")
    public void payment(@RequestBody CreditCompany creditCompany);

    @RequestMapping(method = RequestMethod.POST, path = "/creditCompanies")
    public void refund(@RequestBody CreditCompany creditCompany);
}
