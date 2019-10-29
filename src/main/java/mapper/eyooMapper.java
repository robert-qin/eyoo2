package mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import po.eyoo;
import po.eyooExample;

public interface eyooMapper {
    int countByExample(eyooExample example);

    int deleteByExample(eyooExample example);

    int deleteByPrimaryKey(Integer eyooId);

    int insert(eyoo record);

    int insertSelective(eyoo record);

    List<eyoo> selectByExample(eyooExample example);

    eyoo selectByPrimaryKey(Integer eyooId);

    int updateByExampleSelective(@Param("record") eyoo record, @Param("example") eyooExample example);

    int updateByExample(@Param("record") eyoo record, @Param("example") eyooExample example);

    int updateByPrimaryKeySelective(eyoo record);

    int updateByPrimaryKey(eyoo record);
}