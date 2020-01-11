package com.tang.dbtable.mapper;

import com.tang.dbtable.domain.Goods;

import java.util.List;

/**
 * @Classname ${NAME}
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/1/10 23:35
 * @Created by ASUS
 */
public interface GoodsMapper {

    int deleteByPrimaryKey(Long goodsId);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    List<Goods> selectAll();
}