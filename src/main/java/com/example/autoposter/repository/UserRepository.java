package com.example.autoposter.repository;

import com.example.autoposter.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends JpaRepository<User, Long>{


}
