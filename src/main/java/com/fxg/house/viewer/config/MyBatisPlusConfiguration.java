package com.fxg.house.viewer.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatisPlus 配置类
 */
@Configuration
@MapperScan(value = {"com.fxg.house.viewer.mapper"})
public class MyBatisPlusConfiguration {

	/**
	 * 自定义 MyBatisPlus 分页拦截器
	 * @return
	 */
	@Bean
	public PaginationInterceptor getMySqlPaginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		// 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
		// paginationInterceptor.setOverflow(false);
		// 设置最大单页限制数量，默认 500 条，-1 不受限制
//		 paginationInterceptor.setLimit(500);
		// 开启 count 的 join 优化,只针对部分 left join
		// page.setCountSqlParser(new JsqlParserCountOptimize(true));
		//设置方言类型
		paginationInterceptor.setDbType(DbType.MYSQL);
		return paginationInterceptor;
	}
}