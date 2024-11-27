package hython.secret.Repository;

import hython.secret.Entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepository extends JpaRepository<Images, Integer> {

    Images findByImageId(int imageId);

}
