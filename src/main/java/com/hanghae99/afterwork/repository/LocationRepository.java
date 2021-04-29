package com.hanghae99.afterwork.repository;

import com.hanghae99.afterwork.model.Location;
import com.hanghae99.afterwork.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findAllByUser(User user);


}
