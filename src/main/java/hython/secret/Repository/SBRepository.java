package hython.secret.Repository;

import hython.secret.Entity.SpeechBubbleImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SBRepository extends JpaRepository<SpeechBubbleImage,Integer> {
    SpeechBubbleImage findById(int id);
}
