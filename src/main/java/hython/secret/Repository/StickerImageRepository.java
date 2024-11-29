package hython.secret.Repository;

import hython.secret.Entity.StickerImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StickerImageRepository extends JpaRepository<StickerImage, Integer> {

    StickerImage findById(int id);
}
