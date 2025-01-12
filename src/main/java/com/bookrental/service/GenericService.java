package com.bookrental.service;

import java.util.List;
import java.util.Set;

public interface GenericService<T,ID> {

    public T findByPK(ID id);

    public void saveEntity(T t);

    public void saveMany(List<T> entities);

    public List<T> getAll();

    public void delete(T t);

    public void deleteById(ID id);

    public void deleteAllById(Set<ID> ids);

    public void activeById(ID id);

    public void activeAll(Set<ID> ids);

//    Map<String, Object> getFilteredItems <? extends PaginationRequest> (PaginationRequest request);

//    Page<Map<String, Object>> getFilteredAndPagedItems(T filteredItems, PaginationRequest paginationRequest);
}
