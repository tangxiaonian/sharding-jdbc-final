package com.tang.dbtable.service;

import com.tang.dbtable.domain.Goods;
import com.tang.dbtable.mapper.GoodsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname GoodsService
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/1/11 10:38
 * @Created by ASUS
 */
@Service
public class GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    public List<Goods> selectAll() {

        return goodsMapper.selectAll();

    }

    public Goods selectById(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    public int delGoods(Long id) {
        return goodsMapper.deleteByPrimaryKey(id);
    }

    public int insertGoods(Goods goods) {
        return goodsMapper.insert(goods);
    }
}