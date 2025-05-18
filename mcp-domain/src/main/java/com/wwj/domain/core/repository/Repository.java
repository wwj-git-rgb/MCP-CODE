package com.wwj.domain.core.repository;

import java.util.List;
import java.util.Optional;

/**
 * 通用仓储接口
 *
 * @param <T>  实体类型
 * @param <ID> ID类型
 * @author wenjie wang
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
     * 批量保存实体
     *
     * @param entities 实体列表
     * @return 保存后的实体列表
     */
    List<T> saveAll(Iterable<T> entities);

    /**
     * 根据ID查询实体
     *
     * @param id ID
     * @return 实体对象（可能为空）
     */
    Optional<T> findById(ID id);

    /**
     * 检查实体是否存在
     *
     * @param id ID
     * @return 是否存在
     */
    boolean existsById(ID id);

    /**
     * 查询所有实体
     *
     * @return 实体列表
     */
    List<T> findAll();

    /**
     * 根据ID列表查询实体
     *
     * @param ids ID列表
     * @return 实体列表
     */
    List<T> findAllById(Iterable<ID> ids);

    /**
     * 统计实体数量
     *
     * @return 实体数量
     */
    long count();

    /**
     * 根据ID删除实体
     *
     * @param id ID
     */
    void deleteById(ID id);

    /**
     * 删除实体
     *
     * @param entity 实体
     */
    void delete(T entity);

    /**
     * 批量删除实体
     *
     * @param entities 实体列表
     */
    void deleteAll(Iterable<T> entities);

    /**
     * 清空所有实体
     */
    void deleteAll();
}
