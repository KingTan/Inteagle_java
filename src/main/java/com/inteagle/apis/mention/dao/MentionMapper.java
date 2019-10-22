package com.inteagle.apis.mention.dao;

import com.inteagle.apis.mention.entity.Mention;

public interface MentionMapper {
    int deleteByPrimaryKey(String mentionId);

    int insert(Mention record);

    int insertSelective(Mention record);

    Mention selectByPrimaryKey(String mentionId);

    int updateByPrimaryKeySelective(Mention record);

    int updateByPrimaryKey(Mention record);
}