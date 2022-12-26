package byx.util.jdbc.core;

import java.sql.ResultSet;

/**
 * 行转换器接口
 *
 * @author byx
 */
public interface RowMapper<T> {
    T map(ResultSet resultSet);
}
