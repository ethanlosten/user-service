package eth.tr.test.repository;

import eth.tr.test.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query("from Role role where role.name=:roleName")
    Role findByName(@Param("roleName") String roleName);

    @Query("select count(*) from Role role where role.name=:roleName")
    Integer countByName(@Param("roleName") String roleName);
}
