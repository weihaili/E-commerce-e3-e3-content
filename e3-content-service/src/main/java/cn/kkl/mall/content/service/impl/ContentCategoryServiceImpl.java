package cn.kkl.mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.kkl.mall.content.service.ContentCategoryService;
import cn.kkl.mall.mapper.TbContentCategoryMapper;
import cn.kkl.mall.pojo.E3Result;
import cn.kkl.mall.pojo.EasyUITreeNode;
import cn.kkl.mall.pojo.TbContentCategory;
import cn.kkl.mall.pojo.TbContentCategoryExample;
import cn.kkl.mall.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
		List<EasyUITreeNode> treeNodes = new ArrayList<>();
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		for (TbContentCategory contentCategory : list) {
			EasyUITreeNode treeNode = new EasyUITreeNode();
			treeNode.setId(contentCategory.getId());
			treeNode.setText(contentCategory.getName());
			treeNode.setState(contentCategory.getIsParent()?"closed":"open");
			treeNodes.add(treeNode);
		}
		return treeNodes;
	}

	@Override
	public E3Result addContentCategory(Long parentId, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		Date date = new Date();
		contentCategory.setCreated(date);
		contentCategory.setName(name);
		//The newly added node must be a leaf node
		contentCategory.setIsParent(false);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		//1(normal),2(delete)'
		contentCategory.setStatus(1);
		contentCategory.setUpdated(date);
		contentCategoryMapper.insert(contentCategory);
		//Modify the parent node`s isParent property to true
		TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!tbContentCategory.getIsParent()) {
			tbContentCategory.setIsParent(true);
			tbContentCategory.setUpdated(date);
			contentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
		}
		return E3Result.ok(contentCategory);
	}

	@Override
	public E3Result updateContentCategory(Long id, String name) {
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);
		contentCategory.setUpdated(new Date());
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		return E3Result.ok(contentCategory);
	}

	@Override
	public E3Result deleteContentCategoryById(Long id) {
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		if (!contentCategory.getIsParent()) {
			contentCategoryMapper.deleteByPrimaryKey(id);
			//return E3Result.ok();
		}
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		List<TbContentCategory> contentCategories = contentCategoryMapper.selectByExample(example);
		if (contentCategories!=null && contentCategories.size()==1) {
			TbContentCategory category = contentCategories.get(0);
			category.setIsParent(false);
			category.setUpdated(new Date());
			contentCategoryMapper.updateByPrimaryKeySelective(category);
		}else {
			return E3Result.build(300, "illegal opertation");
		}
		return E3Result.ok();
	}

}
