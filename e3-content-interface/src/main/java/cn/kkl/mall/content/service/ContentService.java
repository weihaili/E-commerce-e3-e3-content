package cn.kkl.mall.content.service;

import java.util.List;

import cn.kkl.mall.pojo.E3Result;
import cn.kkl.mall.pojo.TbContent;

public interface ContentService {
	
	E3Result addContent(TbContent content);
	
	List<TbContent> getContentListByCid(Long cid);

}
