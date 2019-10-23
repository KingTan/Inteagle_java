package com.inteagle.dao.mention;

import com.inteagle.modal.mention.Mention;


/**
 * 
* @ClassName: MentionMapper
* @Description: TODO(这里用一句话描述这个类的作用)
* @author IVAn
* @date 2019年10月23日上午9:33:34
*
 */
public interface MentionMapper {
	
	/**
	 * @description say 
	 * @author IVAn
	 * @date 2019年10月23日 上午9:34:02
	 * @param mentionId
	 * @return
	 */
    int deleteByPrimaryKey(String mentionId);

    int insert(Mention record);

    int insertSelective(Mention record);

    Mention selectByPrimaryKey(String mentionId);

    int updateByPrimaryKeySelective(Mention record);

    int updateByPrimaryKey(Mention record);
}