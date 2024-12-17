package com.wflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wflow.entity.WflowUsers;
import com.wflow.vo.OrgTreeVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WflowUsersMapper extends BaseMapper<WflowUsers> {
    
    @Select("SELECT ou.user_id id, ou.user_name name, 'user' AS type, ou.avatar " +
           "FROM wflow_user_departments oud " +
           "LEFT JOIN wflow_users ou ON ou.user_id = oud.user_id " +
           "WHERE oud.dept_id = #{deptId}")
    List<OrgTreeVo> selectUsersByDept(@Param("deptId") String deptId);

    @Select("SELECT dept_id FROM wflow_user_departments WHERE user_id = #{userId}")
    List<String> selectUserDepts(@Param("userId") String userId);
} 