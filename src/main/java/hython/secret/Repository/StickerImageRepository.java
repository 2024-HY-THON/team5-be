package hython.secret.Repository;

import hython.secret.Entity.CharacterImage;
import hython.secret.Entity.StickerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StickerImageRepository extends JpaRepository<StickerImage, Integer> {

    StickerImage findById(int id);
    List<StickerImage> findAll();

}
