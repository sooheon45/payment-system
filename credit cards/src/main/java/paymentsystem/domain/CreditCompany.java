package paymentsystem.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import paymentsystem.CreditCardsApplication;

@Entity
@Table(name = "CreditCompany_table")
@Data
//<<< DDD / Aggregate Root
public class CreditCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public static CreditCompanyRepository repository() {
        CreditCompanyRepository creditCompanyRepository = CreditCardsApplication.applicationContext.getBean(
            CreditCompanyRepository.class
        );
        return creditCompanyRepository;
    }
}
//>>> DDD / Aggregate Root
