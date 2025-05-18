package com.wwj.infrastructure.weather.repository;

import com.wwj.domain.weather.model.Weather;
import com.wwj.domain.weather.repository.WeatherRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 基于内存的天气数据仓库实现
 * 注意：这是演示用途，实际应用应使用持久化的存储方案
 *
 * @author wenjie wang
 * @since 1.0.0
 */
@Repository
@ConditionalOnProperty(prefix = "mcp.features.weather", name = "enabled", havingValue = "true", matchIfMissing = true)
public class InMemoryWeatherRepository implements WeatherRepository {

    private final Map<String, Weather> weatherStore = new ConcurrentHashMap<>();

    @Override
    public Weather save(Weather entity) {
        weatherStore.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<Weather> saveAll(Iterable<Weather> entities) {
        List<Weather> result = new ArrayList<>();
        entities.forEach(entity -> result.add(save(entity)));
        return result;
    }

    @Override
    public Optional<Weather> findById(String id) {
        return Optional.ofNullable(weatherStore.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return weatherStore.containsKey(id);
    }

    @Override
    public List<Weather> findAll() {
        return new ArrayList<>(weatherStore.values());
    }

    @Override
    public List<Weather> findAllById(Iterable<String> ids) {
        List<Weather> result = new ArrayList<>();
        ids.forEach(id -> findById(id).ifPresent(result::add));
        return result;
    }

    @Override
    public long count() {
        return weatherStore.size();
    }

    @Override
    public void deleteById(String id) {
        weatherStore.remove(id);
    }

    @Override
    public void delete(Weather entity) {
        if (entity != null && entity.getId() != null) {
            deleteById(entity.getId());
        }
    }

    @Override
    public void deleteAll(Iterable<Weather> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        weatherStore.clear();
    }

    @Override
    public Optional<Weather> findByCityName(String cityName) {
        return weatherStore.values().stream()
                .filter(weather -> weather.getCityName().equals(cityName))
                .findFirst();
    }

    @Override
    public Optional<Weather> findByCityCode(String cityCode) {
        return weatherStore.values().stream()
                .filter(weather -> weather.getCityCode().equals(cityCode))
                .findFirst();
    }

    @Override
    public void deleteByCityName(String cityName) {
        List<Weather> toDelete = weatherStore.values().stream()
                .filter(weather -> weather.getCityName().equals(cityName))
                .collect(Collectors.toList());
        
        toDelete.forEach(this::delete);
    }

    @Override
    public void deleteByCityCode(String cityCode) {
        List<Weather> toDelete = weatherStore.values().stream()
                .filter(weather -> weather.getCityCode().equals(cityCode))
                .collect(Collectors.toList());
        
        toDelete.forEach(this::delete);
    }
}
