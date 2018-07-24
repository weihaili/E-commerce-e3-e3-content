package cn.kkl.mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.kkl.mall.content.service.ContentService;
import cn.kkl.mall.mapper.TbContentMapper;
import cn.kkl.mall.pojo.E3Result;
import cn.kkl.mall.pojo.TbContent;
import cn.kkl.mall.pojo.TbContentExample;
import cn.kkl.mall.pojo.TbContentExample.Criteria;
import cn.kkl.mall.service.JedisClient;
import cn.kkl.mall.utils.JsonUtils;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private JedisClient jedisClient;
	
	@Autowired
	private TbContentMapper contentMapper;

	@Value("${CONTENT_LIST}")
	private String contentCacheKey;
	
	@Override
	public E3Result addContent(TbContent content) {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		contentMapper.insertSelective(content);
		//cache synchronization
		try {
			jedisClient.hdel(contentCacheKey, content.getCategoryId().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return E3Result.ok();
	}

	@Override
	public List<TbContent> getContentListByCid(Long cid) {
		//query cache
		try {
			String cacheString = jedisClient.hget(contentCacheKey, String.valueOf(cid));
			if (StringUtils.isNotBlank(cacheString)) {
				List<TbContent> contentList = JsonUtils.jsonToList(cacheString, TbContent.class);
				return contentList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> contents = contentMapper.selectByExampleWithBLOBs(example);
		
		//add cache
		try {
			jedisClient.hset(contentCacheKey, String.valueOf(cid), JsonUtils.objectToJson(contents));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return contents;
	}
	
	

}
