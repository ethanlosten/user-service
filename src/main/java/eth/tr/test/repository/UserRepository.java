package eth.tr.test.repository;

import eth.tr.test.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("from User user where user.name=:userName")
    Iterable<User> findByName(@Param("userName") String userName);

    @Query("select user from User user join user.roles role where role.id=:id")
    Iterable<User> findByRole(@Param("id") Integer id);

}
