package com.ts.spm.bizs.mapper.jzpacm;

import java.util.List;
import java.util.Map;

/**
* @author 作者biguopeng:
* @version 创建时间：2020年9月8日 
* 说明:首页到mapper 
*/
public interface HomeMapper{

	List<Map> selectCount() ;

	List<Map> selectInspectCount();

	List<Map> selectInfo();

	List<Map> selectWrapCount();

	List<Map> selectProhibitCount();

}
