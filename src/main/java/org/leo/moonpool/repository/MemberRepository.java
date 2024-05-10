package org.leo.moonpool.repository;

import org.leo.moonpool.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
