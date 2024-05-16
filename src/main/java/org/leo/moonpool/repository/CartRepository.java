package org.leo.moonpool.repository;

import jakarta.transaction.Transactional;
import org.leo.moonpool.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query(value = "SELECT c ,p FROM Cart c INNER JOIN Problem p ON c.problemId=p.problemId WHERE c.ownerId=:memberId ORDER BY c.cartId DESC ")
    List<?> customFindAll(@Param("memberId") Long memberId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cart WHERE cart.owner_id =?1",nativeQuery = true)
    int customDeleteAll(Long id);

    @Query(value = "SELECT c.problemId FROM Cart c WHERE c.ownerId=:memberId")
    List<Long> customFindByMemberId(@Param("memberId")Long memberId);
}
