package byx.util.jdbc.core;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 将一行数据转换成JavaBean
 *
 * @author byx
 */
public class BeanRowMapper<T> implements RowMapper<T> {
	private final Class<T> type;

	public BeanRowMapper(Class<T> type) {
		this.type = type;
	}

	@Override
	public T map(ResultSet rs) {
		try {
			int count = rs.getMetaData().getColumnCount();
			T bean = type.getDeclaredConstructor().newInstance();
			//排除静态、常量
			Set<String> fieldSet = Arrays.stream(type.getDeclaredFields()).filter(d -> !Modifier.isStatic(d.getModifiers()) && !Modifier.isFinal(d.getModifiers()) && !Modifier.isSynchronized(d.getModifiers())).map(Field::getName).collect(Collectors.toSet());
			for (int i = 1; i <= count; i++) {
				String propertyName = getPropertyName(fieldSet, rs.getMetaData().getColumnLabel(i));
				if (propertyName != null) {
					PropertyDescriptor pd = new PropertyDescriptor(propertyName, type);
					Method setter = pd.getWriteMethod();
					setter.invoke(bean, getValue(pd, rs, i));
				}
			}
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private Object getValue(PropertyDescriptor pd, ResultSet rs, int index) throws SQLException {
		String typeName = pd.getPropertyType().getTypeName();
		if (typeName.equals(String.class.getTypeName())) {
			return rs.getString(index);
		} else if (typeName.equals(BigDecimal.class.getTypeName())) {
			return rs.getBigDecimal(index);
		} else if (typeName.equals(Double.class.getTypeName())) {
			return rs.getDouble(index);
		} else if (typeName.equals(Float.class.getTypeName())) {
			return rs.getFloat(index);
		} else if (typeName.equals(Long.class.getTypeName())) {
			return rs.getLong(index);
		} else if (typeName.equals(Integer.class.getTypeName())) {
			return rs.getInt(index);
		} else if (typeName.equals(Short.class.getTypeName())) {
			return rs.getShort(index);
		} else if (typeName.equals(Boolean.class.getTypeName())) {
			return rs.getBoolean(index);
		} else if (typeName.equals(Byte.class.getTypeName())) {
			return rs.getByte(index);
		} else if (typeName.equals(Date.class.getTypeName())) {
			Timestamp timestamp = rs.getTimestamp(index);
			if (timestamp != null) {
				return new Date(timestamp.getTime());
			}
			//rs.getDate(index)
			return null;
		} else if (typeName.equals(java.sql.Time.class.getTypeName())) {
			return rs.getTime(index);
		} else if (typeName.equals(java.sql.Timestamp.class.getTypeName())) {
			return rs.getTimestamp(index);
		}
		return rs.getObject(index);
	}

	/**
	 * 获取db-bean字段
	 *
	 * @param fieldSet     bean所有字段
	 * @param propertyName db字段
	 * @return 通过转换得到bean存在的字段
	 */
	private String getPropertyName(Set<String> fieldSet, String propertyName) {
		if (fieldSet.contains(propertyName)) {
			return propertyName;
		}
		String conversionField = null;
		if (propertyName.contains("_")) {
			conversionField = propertyName.replaceAll("_", "");
		}
		for (String field : fieldSet) {
			if (field.equalsIgnoreCase(propertyName) || field.equalsIgnoreCase(conversionField)) {
				return field;
			}
		}
		return null;
	}

}
