package byx.util.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 转换只有一个列的数据行
 *
 * @author byx
 */
public class SingleColumnRowMapper<T> implements RowMapper<T> {
	@Override
	@SuppressWarnings("unchecked")
	public T map(ResultSet rs) {
		try {
			return (T) rs.getObject(1);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
