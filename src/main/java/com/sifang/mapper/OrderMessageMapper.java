package com.sifang.mapper;

import com.sifang.pojo.OrderMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderMessageMapper {
    List<OrderMessage> getOrderMessageByNumberId(int numberId);
    int addOrderMessage(OrderMessage orderMessage);
    List<OrderMessage> getOrderMessageByUserId(int userId);
    int returnNumber(int id);
    OrderMessage getOrderMessageById(int id);
    //获取某位患者在同一天中的预约信息
    List<OrderMessage> getOrderMessageByAccount(String account, String numberDate);
    int setStatus(int status, int numberId);
}
