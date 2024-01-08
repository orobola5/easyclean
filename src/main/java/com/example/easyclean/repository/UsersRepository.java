package com.example.easyclean.repository;

import com.example.easyclean.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    Users findByEmail(String email);

    @Query(nativeQuery = true,value = "select * from users")
    List<Users> findUsers();

    @Query("select a from Users a where a.firstName = :name order by a.id desc")
    Page<Users> filterByName(@Param("name") String name, Pageable pageable);

    @Query("select a from Users a where a.email = :email order by a.id desc")
    Page<Users> filterByEmail(@Param("email") String email, Pageable pageable);

    @Query("select a from Users a where a.phone = :phone order by a.id desc")
    Page<Users> filterByPhone(@Param("phone") String phone, Pageable pageable);

    Boolean existsByEmail(String email);

    //Users findByLocation(@Param("address")String address,Pageable pageable);
}
