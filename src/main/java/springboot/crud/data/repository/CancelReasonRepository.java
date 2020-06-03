package springboot.crud.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.crud.data.entity.CancelReasonEntity;

@Repository
public interface CancelReasonRepository extends JpaRepository<CancelReasonEntity, Integer> {}
