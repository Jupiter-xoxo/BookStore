package th.co.bookstore.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import th.co.bookstore.dao.entity.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

	Order findByUserIdAndIsDeleted(Integer userId, String deleted);
}
