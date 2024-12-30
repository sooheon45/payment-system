package paymentsystem.external;

import java.util.Date;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "cash", url = "${api.url.cash}")
public interface StoreService {
    @RequestMapping(method = RequestMethod.POST, path = "/stores")
    public void payment(@RequestBody Store store);

    @RequestMapping(method = RequestMethod.POST, path = "/stores")
    public void refund(@RequestBody Store store);
}
