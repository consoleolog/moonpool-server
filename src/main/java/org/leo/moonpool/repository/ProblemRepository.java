package org.leo.moonpool.repository;

import org.leo.moonpool.entity.Problem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query(value = "SELECT p FROM Problem p WHERE p.category=:category ORDER BY p.problemId DESC LIMIT 20 OFFSET :offset")
    List<?> customeFindAll(@Param("category")String category,@Param("offset") int offset);

    @Query(value = "SELECT COUNT (p) FROM Problem p WHERE p.category=:category")
    Long countByCategory(@Param("category") String category);


}
