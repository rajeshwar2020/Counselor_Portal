package com.rajeshIT.repositories;

import com.rajeshIT.entities.Counselor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CounselorRepository extends JpaRepository<Counselor, Integer> {

    @Query(value = "select * from COUNSELORS_TABLE where EMAIL = :email", nativeQuery = true)
    public Optional<Counselor> findByEmail(String email);

    public Counselor findByEmailAndPassword(String email, String password);
}
