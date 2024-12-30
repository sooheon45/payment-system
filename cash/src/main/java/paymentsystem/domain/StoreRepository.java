package paymentsystem.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import paymentsystem.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "stores", path = "stores")
public interface StoreRepository
    extends PagingAndSortingRepository<Store, Long> {}
