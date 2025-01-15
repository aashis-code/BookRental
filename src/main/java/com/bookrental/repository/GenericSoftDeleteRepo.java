package com.bookrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@NoRepositoryBean
public interface GenericSoftDeleteRepo<T, ID> extends JpaSpecificationExecutor<T>, JpaRepository<T, ID> {

    @Modifying
    @Transactional
    @Query("update #{#entityName} e set e.deleted=true where e.id=?1")
    void undoSoftDeleteOfId(ID id);

    @Modifying
    @Transactional
    @Query("update #{#entityName} e set e.deleted=true where e.id in ?1")
    void softDeleteOfIds(Collection<ID> ids);

    @Modifying
    @Transactional
    @Query("update #{#entityName} e set e.deleted=true where e.id=?1")
    int softDeleteOfId(ID id);

    @Modifying
    @Transactional
    @Query("update #{#entityName} e set e.deleted=false where e.id in ?1")
    void undoSoftDeleteOfSelectedIds(Collection<ID> ids);


}
