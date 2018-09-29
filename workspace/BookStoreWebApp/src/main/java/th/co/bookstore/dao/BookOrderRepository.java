package th.co.bookstore.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import th.co.bookstore.dao.entity.BookOrder;

@Repository
public interface BookOrderRepository extends CrudRepository<BookOrder, Integer> {

	List<BookOrder> findByOrderId(Integer orderId);
}
