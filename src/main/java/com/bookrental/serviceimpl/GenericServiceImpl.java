package com.bookrental.serviceimpl;

import com.bookrental.exceptions.AppException;
import com.bookrental.repository.GenericRepo;
import com.bookrental.service.GenericService;

import java.util.List;
import java.util.Set;

public class GenericServiceImpl<T, ID> implements GenericService<T, ID> {

    private final GenericRepo<T, ID> genericRepo;

    public GenericServiceImpl(GenericRepo<T, ID> genericRepo) {
        this.genericRepo = genericRepo;
    }

    @Override
    public T findByPK(ID id) {
        return this.genericRepo.findById(id).orElseThrow(() -> new AppException(String.format("%s of Id : %s not found", this.getClass().getName(), id.toString())));
    }

    @Override
    public void saveEntity(T t) {
        this.genericRepo.save(t);
    }

    @Override
    public void saveMany(List<T> entities) {
        this.genericRepo.saveAll(entities);
    }

    @Override
    public List<T> getAll() {
        return this.genericRepo.findAll();
    }

    @Override
    public void delete(T t) {
        this.genericRepo.delete(t);
    }

    @Override
    public void deleteById(ID id) {

        if(id == null || (id instanceof Integer && ((Integer)id).intValue() < 1))
            throw new AppException("Provide valid id to delete entity.");
        if(this.genericRepo.softDeleteOfId(id)<1)
            throw new AppException(String.format("%s of Id : %s not found", this.getClass().getName(), id.toString()));
    }

    public void deleteAll(List<ID> ids) {
        this.genericRepo.softDeleteOfIds(ids);
    }

    @Override
    public void deleteAllById(Set<ID> ids) {
        this.genericRepo.softDeleteOfIds(ids);
    }

    @Override
    public void activeById(ID id) {
        this.genericRepo.undoSoftDeleteOfId(id);
    }

    @Override
    public void activeAll(Set<ID> ids) {
        this.genericRepo.undoSoftDeleteOfSelectedIds(ids);
    }

}
