package hython.secret.Repository;

import hython.secret.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Boolean existsByRefreshToken(String refreshToken);

    void deleteByEmail(String email);

    @Transactional
    void deleteByRefreshToken(String refreshToken);
}
