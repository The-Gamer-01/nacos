package com.alibaba.nacos.plugin.datasource.mapper.base;

/**
 * The parent class of the all mappers.
 *
 * @author hyx
 **/

public interface Mapper {
    
    /**
     * Get the name of table.
     * @return The name of table.
     */
    String getTableName();
}
