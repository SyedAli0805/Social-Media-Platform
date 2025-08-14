package com.example.social.repository;
import com.example.social.model.Follow;
import com.example.social.model.User;
import org.springframework.data.jpa.repository.*;
import java.util.*;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowing(User follower, User following);
    List<Follow> findByFollower(User follower);
    List<Follow> findByFollowing(User following);
}

