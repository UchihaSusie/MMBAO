package com.rsa.webapi.goods.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rsa.webapi.goods.entity.GgGoods;
import com.rsa.webapi.goods.entity.GgSupplier;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品管理-供应商(GgSupplier)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-14 23:07:25
 */
public interface GgSupplierDao extends BaseMapper<GgSupplier> {

    @Select("SELECT g.* from gg_goods g, gg_supplier_goods r where r.gid = g.id AND r.sid = #{id}")
    List<GgGoods> goodBySupplyId(Integer id);

    @Delete("DELETE FROM `gg_supplier_goods` where sid = #{sid} and gid = #{gid}")
    void deleteGoodId(@Param("gid") Integer gid, @Param("sid") Integer sid);

    @Insert("INSERT INTO gg_supplier_goods VALUES (#{sid},#{gid});")
    void addGoodId(@Param("gid") Integer gid, @Param("sid") Integer sid);
}

