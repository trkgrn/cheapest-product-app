package com.trkgrn_theomer.cheapspring.api.repository;

import com.trkgrn_theomer.cheapspring.api.model.concretes.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
   public User findByUsername(String username);

   @Query(nativeQuery = true,
            value = "SELECT *" +
                    "FROM public.user u" +
                    " WHERE u.user_id!=:#{#userId} " +
                    " AND u.user_id NOT IN (SELECT f.friended_id" +
                    "   FROM friend f" +
                    "   WHERE f.friender_id=:#{#userId}" +
                    " UNION " +
                    "SELECT f.friender_id" +
                    "   FROM friend f" +
                    "   WHERE f.friended_id=:#{#userId})")
   public List<User> searchCandidateFriendByUserId(Long userId);

   @Query(nativeQuery = true,
           value = "SELECT * " +
                   "FROM public.user u" +
                   " WHERE lower(u.user_username) LIKE lower(concat(:#{#username},'%')) AND  u.user_id!=:#{#userId}" +
                   " AND u.user_id NOT IN (SELECT f.friended_id" +
                   "   FROM friend f" +
                   "   WHERE f.friender_id=:#{#userId}" +
                   " UNION " +
                   " SELECT f.friender_id" +
                   "   FROM friend f" +
                   "   WHERE f.friended_id=:#{#userId})")
   public List<User> searchCandidateFriendByUserIdAndUsername(Long userId, String username, Pageable pageable);

}
