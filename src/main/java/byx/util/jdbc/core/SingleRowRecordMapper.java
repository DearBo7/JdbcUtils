package byx.util.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 转换只有一行的结果集
 *
 * @author byx
 */
public class SingleRowRecordMapper<T> implements RecordMapper<T> {
    private final RowMapper<T> rowMapper;

    public SingleRowRecordMapper(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public T map(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return rowMapper.map(rs);
        }
        return null;
    }
}
