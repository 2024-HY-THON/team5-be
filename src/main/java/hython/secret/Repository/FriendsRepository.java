package hython.secret.Repository;

import hython.secret.Entity.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, Integer> {
    Friends save(Friends friends);
    Optional<Friends> findById(int id);
}
