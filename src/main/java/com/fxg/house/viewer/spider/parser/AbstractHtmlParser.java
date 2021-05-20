package com.fxg.house.viewer.spider.parser;

import java.io.IOException;
import java.util.List;

public interface AbstractHtmlParser<T> {

	 T pars(String url) throws IOException;

	abstract List<T> parsList(String url) throws IOException;

}
