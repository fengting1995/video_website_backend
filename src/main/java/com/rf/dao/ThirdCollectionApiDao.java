package com.rf.dao;

import com.rf.model.ThirdCollectionApiModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface ThirdCollectionApiDao {

    @Results(id = "BaseResultMap", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR),
            @Result(column = "key", property = "key", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "bind_id", property = "bindId", jdbcType = JdbcType.VARCHAR)
    })
    @Select("SELECT id, url, `key`, `name`, bind_id FROM third_collection_api WHERE id = #{id,jdbcType=INTEGER}")
    public ThirdCollectionApiModel selectByPrimaryKey(@Param("id")Integer id);

    @ResultMap("BaseResultMap")
    @Select("SELECT id, url, `key`, `name`, bind_id FROM third_collection_api")
    public List<ThirdCollectionApiModel> selectAll();

    @Delete("delete from third_collection_api where id = #{id,jdbcType=INTEGER}")
    public int deleteByPrimaryKey(@Param("id")Integer id);

    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("insert into third_collection_api"+"(url,`name`,`key`,bind_id)"+"(#{url},#{name},#{key},#{bind_id)")
    public void insert(ThirdCollectionApiModel thirdCollectionApiModel);


    @Update("UPDATE third_collection_api " +
            "SET url = #{url,jdbcType=VARCHAR},`key` = #{key,jdbcType=VARCHAR},"+
            "`name` = #{name,jdbcType=VARCHAR},bind_id = #{bindId,jdbcType=VARCHAR}" +
            "WHERE id = #{id,jdbcType=INTEGER}")
    void update(ThirdCollectionApiModel thirdCollectionApiModel);

}
