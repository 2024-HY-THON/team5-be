package hython.secret.Repository;

import java.util.*;
import hython.secret.Entity.Belog;
import hython.secret.Entity.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BelogRepository extends JpaRepository<Belog, Integer> {
    Belog findById(int belogId);
    List<Belog> findByScope(Scope scope);

    // Scope가 ALL인 Belog를 랜덤으로 3개 조회 (SQL로 처리)
    @Query(value = "SELECT * FROM belog WHERE scope = :scope ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Belog> findRandomByScope(@Param("scope") String scope, @Param("limit") int limit);
}
