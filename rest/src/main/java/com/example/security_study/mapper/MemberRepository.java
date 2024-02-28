package com.example.security_study.mapper;

import com.example.security_study.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member , Long>  {

    Optional<Member> findByUserId(String userName);

    @Override
    Optional<Member> findById(Long aLong);

    @Override
    <S extends Member> S save(S entity);
}
