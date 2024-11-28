package hython.secret.Repository;

import hython.secret.Entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tags, Integer> {

    Tags save(Tags tags);
    Tags findByName(String name);

}
