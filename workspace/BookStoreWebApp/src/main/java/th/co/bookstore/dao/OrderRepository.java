package th.co.bookstore.dao;

import org.springframework.data.repository.CrudRepository;

import th.co.bookstore.dao.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {

	Order findByUserIdAndIsDeleted(Integer userId, String deleted);
}
