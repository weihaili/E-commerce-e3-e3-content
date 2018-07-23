package cn.kkl.mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.kkl.mall.content.service.ContentService;
import cn.kkl.mall.mapper.TbContentMapper;
import cn.kkl.mall.pojo.E3Result;
import cn.kkl.mall.pojo.TbContent;
import cn.kkl.mall.pojo.TbContentExample;
import cn.kkl.mall.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper;

	@Override
	public E3Result addContent(TbContent content) {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		contentMapper.insertSelective(content);
		return E3Result.ok();
	}

	@Override
	public List<TbContent> getContentListByCid(Long cid) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> contents = contentMapper.selectByExampleWithBLOBs(example);
		return contents;
	}
	
	

}
