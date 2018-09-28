package th.co.bookstore.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import th.co.bookstore.dao.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	User findByUsernameAndIsDeleted(String username, String isDeleted);
}
