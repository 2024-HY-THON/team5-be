package hython.secret.Repository;

import hython.secret.Entity.Consent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsentRepository extends JpaRepository<Consent, Integer> {

    Consent findByConsentName(String consentName);
}
