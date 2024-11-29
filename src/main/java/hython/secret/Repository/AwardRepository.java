package hython.secret.Repository;

import hython.secret.Entity.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardRepository extends JpaRepository<Award, Integer> {

    Award save(Award award);

}
