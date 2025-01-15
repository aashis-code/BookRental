package com.bookrental.repository;

import com.bookrental.model.RolePermissionMapping;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepo extends GenericRepo<RolePermissionMapping, Integer> {


    @Query(value = "select p.name from role_permission_mapping rpm\n" +
            "left join permission p on p.id=rpm.permission_id\n" +
            "left join role r on r.id=rpm.role_id where r.name=?1;", nativeQuery = true)
    List<String> getPermissionsByRole(String roleName);

}
