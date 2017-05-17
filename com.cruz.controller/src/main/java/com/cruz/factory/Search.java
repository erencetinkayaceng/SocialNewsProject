package com.cruz.factory;

import java.util.List;

public interface Search {

	List<?> doSearchByPage(String searchWords, int pageNum, int pageSize);

	int getTotalCount(String searchWords, int totalCount);
}
