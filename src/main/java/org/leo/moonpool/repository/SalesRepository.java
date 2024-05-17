package org.leo.moonpool.repository;

import org.leo.moonpool.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales,Long> {

    @Query(value = "SELECT s.problemId FROM Sales s WHERE s.memberId=:memberId")
    List<Long> customFindByMemberId(@Param("memberId") Long memberId);

    @Query(value = "SELECT p FROM Problem p INNER JOIN Sales s ON s.problemId=p.problemId WHERE s.memberId=:memberId ORDER BY s.salesId DESC LIMIT 10 OFFSET :offset")
    List<?> customFindAll(@Param("memberId")Long memberId,@Param("offset")int offset);

    @Query(value = "SELECT COUNT (p) FROM Problem p INNER JOIN Sales s ON s.problemId=p.problemId WHERE s.memberId=:memberId")
    Long countByMemberId(@Param("memberId")Long memberId);
    @Query(value = "SELECT p FROM Problem p WHERE p.writerId=:memberId ORDER BY p.problemId DESC LIMIT 10 OFFSET :offset")
    List<?> customFindMadeList(@Param("memberId") Long memberId,@Param("offset")int offset);
    @Query(value = "SELECT COUNT (p) FROM Problem p WHERE p.writerId=:memberId")
    Long countByMemberIdForMadeList(@Param("memberId")Long memberId);
    // 문제 아이디 가지고 작성자 찾는거임
    @Query(value = "SELECT p.writerId FROM Problem p WHERE p.problemId=:problemId")
    Long findMemberIdByProblemId(@Param("problemId")Long problemId);
}
