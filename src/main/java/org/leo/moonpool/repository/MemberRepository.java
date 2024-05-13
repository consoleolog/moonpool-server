package org.leo.moonpool.repository;

import org.leo.moonpool.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByUsername(String username);

    Optional<Member> findByMemberId(Long memberId);

    @Query(value = "SELECT m FROM Member m WHERE m.memberId=:memberId AND m.username=:username")
    Member customFindByMemberIdAndUsername(@Param("memberId")Long memberId,@Param("username")String username);
}
