package hython.secret.Repository;

import hython.secret.Entity.User_consent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConsentRepository extends JpaRepository<User_consent, Integer> {

    User_consent save(User_consent user_consent);
}
