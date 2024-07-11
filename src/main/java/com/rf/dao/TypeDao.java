package com.rf.dao;

import com.rf.model.TypeModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TypeDao {

    public List<TypeModel> selectAll();

}
