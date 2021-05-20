package com.fxg.house.viewer.config;

import com.fxg.house.viewer.enums.BaseEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis 默认的枚举转换器是 org.apache.ibatis.type.EnumTypeHandler
 * 我们一般都会自定义枚举类型，一般也会自定义枚举和数据库字段类型的映射（默认的是把枚举的名字映射成char类型），
 * 所以这里根据是不是我们自定义的枚举类型决定用默认的还是用我们自定义的枚举转换器
 *
 * 这个自定义的枚举转换器可以在 application.yml 配置文件中配置，
 * 也可以在 mapper.xml 中的 <result></result>中配置，
 * 还可以在 mybatis 配置文件的 <typeHandlers></typeHandlers> 中配置。
 * @param <E>
 */
public class AutoEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

	private BaseTypeHandler typeHandler = null;

	public AutoEnumTypeHandler(Class<E> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		if(BaseEnum.class.isAssignableFrom(type)){
			// 如果实现了 BaseCodeEnum 则使用自定义的转换器
			typeHandler = new MyEnumTypeHandler(type);
		}else {
			// 默认转换器 也可换成 EnumOrdinalTypeHandler
			typeHandler = new EnumTypeHandler<>(type);
		}
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		typeHandler.setNonNullParameter(ps,i, parameter,jdbcType);
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return (E) typeHandler.getNullableResult(rs,columnName);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return (E) typeHandler.getNullableResult(rs,columnIndex);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return (E) typeHandler.getNullableResult(cs,columnIndex);
	}
}
