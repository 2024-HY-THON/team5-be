package hython.secret.Repository;

import hython.secret.Entity.HatImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HatImageRepository extends JpaRepository<HatImage, Integer> {

    HatImage findById(int id);
}
