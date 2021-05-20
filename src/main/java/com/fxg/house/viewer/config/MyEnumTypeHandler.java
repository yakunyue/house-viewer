package com.fxg.house.viewer.config;

import com.fxg.house.viewer.enums.BaseEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 自定义 mybatis 枚举转换器：
 * 将 domain 中的枚举字段映射成mysql数据库中的整形字段（通过value值映射）
 * @param <T>
 */
public class MyEnumTypeHandler<T extends Enum<?> & BaseEnum> extends BaseTypeHandler<BaseEnum> {

	private Class<T> type;

	public MyEnumTypeHandler(Class<T> type) {
		this.type = type;
	}

	@Override
	public void setNonNullParameter(PreparedStatement preparedStatement, int i, BaseEnum baseEnum, JdbcType jdbcType) throws SQLException {
		preparedStatement.setInt(i, baseEnum.getValue());
	}

	@Override
	public BaseEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
		int value = resultSet.getInt(s);
		return resultSet.wasNull() ? null : valueOf(value);
	}

	@Override
	public BaseEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
		int value = resultSet.getInt(i);
		return resultSet.wasNull() ? null : valueOf(value);
	}

	@Override
	public BaseEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
		int value = callableStatement.getInt(i);
		return callableStatement.wasNull() ? null : valueOf(value);
	}

	private T valueOf(int value) {
		T[] enumConstants = type.getEnumConstants();
		for (T e : enumConstants) {
			if (e.getValue() == value)
				return e;
		}
		return null;
	}
}
