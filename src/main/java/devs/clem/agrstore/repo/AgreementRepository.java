package devs.clem.agrstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import devs.clem.agrstore.models.Agreement;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Long> {
    
}
