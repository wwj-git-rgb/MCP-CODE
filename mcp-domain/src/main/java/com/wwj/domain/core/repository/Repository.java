package com.wwj.domain.core.repository;

import java.util.Optional;

/**
 * 通用仓储接口
 *
 * @author wenjie
 * @since 1.0.0
 */
public interface Repository<T, ID> {
    /**
     * 保存实体
     *
     * @param entity 实体
     * @return 保存后的实体
     */
    T save(T entity);

    /**
     * 根据ID查询实体
     *
     * @param id ID
     * @return 实体可选项
     */
    Optional<T> findById(ID id);

    /**
     * 查询所有实体
     *
     * @return 实体集合
     */
    Iterable<T> findAll();

    /**
     * 删除实体
     *
     * @param entity 实体
     */
    void delete(T entity);

    /**
     * 根据ID删除实体
     *
     * @param id ID
     */
    void deleteById(ID id);
}