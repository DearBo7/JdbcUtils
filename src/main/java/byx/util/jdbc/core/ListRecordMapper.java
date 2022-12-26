package byx.util.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 将结果集转换成列表
 *
 * @author byx
 */
public class ListRecordMapper<T> implements RecordMapper<List<T>> {
	private final RowMapper<T> rowMapper;

	public ListRecordMapper(RowMapper<T> rowMapper) {
		this.rowMapper = rowMapper;
	}

	@Override
	public List<T> map(ResultSet record) throws SQLException {
		List<T> result = new ArrayList<>();
		while (record.next()) {
			result.add(rowMapper.map(record));
		}
		return result;
	}
}
