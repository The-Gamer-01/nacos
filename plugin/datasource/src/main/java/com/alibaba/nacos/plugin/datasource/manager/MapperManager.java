package com.alibaba.nacos.plugin.datasource.manager;

import com.alibaba.nacos.common.spi.NacosServiceLoader;
import com.alibaba.nacos.plugin.datasource.mapper.base.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * DataSource Plugin Mapper Management.
 *
 * @author hyx
 **/

public class MapperManager implements InitAndClosable {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MapperManager.class);
    
    private static final MapperManager INSTANCE = new MapperManager();
    
    private MapperManager() {}
    
    public static MapperManager instance() {
        return INSTANCE;
    }
    
    private static final Map<String, Mapper> MAPPER_SPI_MAP = new HashMap<>();
    
    @Override
    public void loadInitial() {
        Collection<Mapper> mappers = NacosServiceLoader.load(Mapper.class);
        for(Mapper mapper : mappers) {
            MAPPER_SPI_MAP.put(mapper.getTableName(), mapper);
            LOGGER.info("[MapperManager] Load Mapper({}) tableName({}) successfully.",
                    mapper.getClass(), mapper.getTableName());
        }
    }
    
    public static synchronized void join(Mapper mapper) {
        if(Objects.isNull(mapper)) {
            return;
        }
        MAPPER_SPI_MAP.put(mapper.getTableName(), mapper);
        LOGGER.warn("[MapperManager] join successfully.");
    }
    
    @Override
    public boolean close() {
        return false;
    }
    
    public Optional<Mapper> findMapper(String tableName) {
        return Optional.ofNullable(MAPPER_SPI_MAP.get(tableName));
    }
}
