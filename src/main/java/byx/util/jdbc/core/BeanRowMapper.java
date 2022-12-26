package byx.util.jdbc.core;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 将一行数据转换成JavaBean
 *
 * @author byx
 */
public class BeanRowMapper<T> implements RowMapper<T> {
	private final Class<T> clazz;

	public BeanRowMapper(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T map(ResultSet rs) {
		try {
			if (rs.next()) {
				return handle(rs, getBeanField());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	public T handle(ResultSet rs, Set<String> fieldSet) throws Exception {
		int count = rs.getMetaData().getColumnCount();
		T bean = clazz.getDeclaredConstructor().newInstance();
		for (int i = 1; i <= count; i++) {
			String propertyName = getPropertyName(fieldSet, rs.getMetaData().getColumnLabel(i));
			if (propertyName != null) {
				PropertyDescriptor pd = new PropertyDescriptor(propertyName, clazz);
				Method setter = pd.getWriteMethod();
				setter.invoke(bean, getValue(pd.getPropertyType().getTypeName(), rs, i));
			}
		}
		return bean;
	}

	public Set<String> getBeanField() {
		Set<String> fieldSet = new HashSet<>();
		for (Field field : clazz.getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers()) && !Modifier.isSynchronized(field.getModifiers())) {
				fieldSet.add(field.getName());
			}
		}
		return fieldSet;
	}

	public Object getValue(String typeName, ResultSet rs, int index) throws SQLException {
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

	public String getPropertyName(Set<String> fieldSet, String propertyName) {
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
