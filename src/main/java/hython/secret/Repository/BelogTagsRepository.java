package hython.secret.Repository;

import hython.secret.Entity.Belog_Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BelogTagsRepository extends JpaRepository<Belog_Tags, Integer> {

}
