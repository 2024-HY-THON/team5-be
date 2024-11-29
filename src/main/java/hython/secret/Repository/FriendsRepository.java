package hython.secret.Repository;

import java.util.*;

import hython.secret.Entity.Belog;
import hython.secret.Entity.Friends;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, Integer> {
    Friends save(Friends friends);
    Optional<Friends> findById(int id);

    //친구 목록 조회 쿼리
//    @Query("SELECT f FROM Friends f WHERE f.user.userId = :userId AND f.status = 'ACCEPTED'")
//    List<Friends> findAcceptedFriendsByUserId(@Param("userId") int userId);
//
//    //친구 목록 가져오기:
//    @Query("SELECT f.friendNickName FROM Friends f WHERE f.user.userId = :userId AND f.status = 'ACCEPTED'")
//    List<String> findFriendNickNamesByUserId(@Param("userId") int userId);
//
//
//    //친구의 별록 조회:
//    @Query("SELECT b FROM Belog b WHERE b.user.nickName IN :friendNickNames AND (b.scope = 'ALL' OR b.scope = 'FRIENDS')")
//    List<Belog> findBelogsByFriendNickNames(@Param("friendNickNames") List<String> friendNickNames);

    //친구의 고유 ID 가져오기
    @Query("SELECT f.counterId FROM Friends f WHERE f.user.userId = :userId AND f.status = hython.secret.Entity.FriendShipStatus.ACCEPT")
    List<Integer> findFriendIdsByUserId(@Param("userId") int userId);

//    //친구 요청을 수락하거나 거절하는 로직 예시:
//    @Transactional
//    @Modifying
//    @Query("UPDATE Friends f SET f.status = 'ACCEPTED' WHERE f.id = :friendId")
//    void acceptFriendRequest(@Param("friendId") int friendId);
//
//    @Transactional
//    @Modifying
//    @Query("UPDATE Friends f SET f.status = 'REJECTED' WHERE f.id = :friendId")
//    void rejectFriendRequest(@Param("friendId") int friendId);



}
