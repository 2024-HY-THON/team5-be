package hython.secret.Repository;

import hython.secret.Entity.CharacterImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterImgRepository extends JpaRepository<CharacterImage,Integer> {
    CharacterImage findById(int id);
}
