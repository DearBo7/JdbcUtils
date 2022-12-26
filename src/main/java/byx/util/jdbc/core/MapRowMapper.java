package byx.util.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

/**
 * 将一行数据转换成Map
 *
 * @author byx
 */
public class MapRowMapper implements RowMapper<Map<String, Object>> {
	@Override
	public Map<String, Object> map(ResultSet rs) {
		Map<String, Object> map = new Hashtable<>();
		try {
			int count = rs.getMetaData().getColumnCount();
			for (int i = 1; i <= count; i++) {
				String key = rs.getMetaData().getColumnLabel(i);
				Object value = rs.getObject(i);
				map.put(key, value);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return map;
	}
}
