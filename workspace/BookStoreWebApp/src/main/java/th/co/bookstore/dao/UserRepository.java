package th.co.bookstore.dao;

import org.springframework.data.repository.CrudRepository;

import th.co.bookstore.dao.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	User findByUsername(String username);
}
