package hython.secret.Repository;

import java.util.*;
import hython.secret.Entity.Belog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BelogRepository extends JpaRepository<Belog, Integer> {
//    List<Belog> findByUserId(int userId);
}
