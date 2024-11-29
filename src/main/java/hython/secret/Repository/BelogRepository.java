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

    @Query("SELECT COALESCE(SUM(b.belogLikeCount), 0) FROM Belog b WHERE b.user.userId = :userId")
    long sumLikesByUser(@Param("userId") int userId);

//    @Query("SELECT b FROM Belog b WHERE b.user.nickName IN :friendNickNames AND (b.scope = 'ALL' OR b.scope = 'FRIENDS')")
//    List<Belog> findBelogsByFriendNickNames(@Param("friendNickNames") List<String> friendNickNames);

    //친구의 고유 ID 기준으로 별록 조회
    @Query("SELECT b FROM Belog b WHERE b.user.userId IN :friendIds AND (b.scope = hython.secret.Entity.Scope.ALL OR b.scope = hython.secret.Entity.Scope.FRIENDS)")
    List<Belog> findBelogsByFriendIds(@Param("friendIds") List<Integer> friendIds);



}
