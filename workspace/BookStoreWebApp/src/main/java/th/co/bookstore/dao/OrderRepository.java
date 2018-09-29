package th.co.bookstore.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import th.co.bookstore.dao.entity.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

	List<Order> findByUserIdAndIsDeleted(Integer userId, String deleted);
	
	@Query(value = " select IFNULL(MAX(id), 0) from orders ", nativeQuery = true)
	String findLastTransactionSeq();
}
