package paymentsystem.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import paymentsystem.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "creditCompanies",
    path = "creditCompanies"
)
public interface CreditCompanyRepository
    extends PagingAndSortingRepository<CreditCompany, Long> {}
