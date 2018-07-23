package cn.kkl.mall.content.service;

import java.util.List;

import cn.kkl.mall.pojo.E3Result;
import cn.kkl.mall.pojo.EasyUITreeNode;

public interface ContentCategoryService {
	
	List<EasyUITreeNode> getContentCategoryList(Long parentId);
	
	E3Result addContentCategory(Long parentId,String name);

	E3Result updateContentCategory(Long id, String name);

	E3Result deleteContentCategoryById(Long id);

}
