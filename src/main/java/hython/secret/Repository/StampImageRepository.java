package hython.secret.Repository;

import hython.secret.Entity.StampImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StampImageRepository extends JpaRepository<StampImage, Integer> {

    StampImage findById(int id);
}
