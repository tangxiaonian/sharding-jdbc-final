package com.tang.dbtable.controller;

import com.tang.dbtable.domain.Goods;
import com.tang.dbtable.service.GoodsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * @Classname GoodsController
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/1/11 10:37
 * @Created by ASUS
 */
@RestController
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @GetMapping("/selectAll")
    public List<Goods> selectAll() {
        return goodsService.selectAll();
    }

    @GetMapping("/id")
    public Goods selectById(Long id) {
        return goodsService.selectById(id);
    }

    @GetMapping("/del")
    public int delGoods(Long id) {
        return goodsService.delGoods(id);
    }

    @GetMapping("/insert")
    public String insertGoods() {

        Goods goods = new Goods();

        for (int i = 0; i < 40; i++) {
            goods.setGoodsId((long) i);
            goods.setGoodsName(LocalDate.now().toString() + "tt");
            goods.setGoodsType((long) i);
            goodsService.insertGoods(goods);

        }

        return "success";

    }


}