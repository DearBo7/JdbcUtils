package byx.util.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 结果集转换器接口
 *
 * @author byx
 */
public interface RecordMapper<T> {
    T map(ResultSet record) throws SQLException;
}
