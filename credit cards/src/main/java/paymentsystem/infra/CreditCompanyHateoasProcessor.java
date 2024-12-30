package paymentsystem.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import paymentsystem.domain.*;

@Component
public class CreditCompanyHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<CreditCompany>> {

    @Override
    public EntityModel<CreditCompany> process(
        EntityModel<CreditCompany> model
    ) {
        return model;
    }
}
