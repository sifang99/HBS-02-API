package com.sifang.service;

import com.sifang.pojo.OrderMessage;
import com.sifang.pojo.ReturnMessage;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface OrderMessageService {
    List<Map<String, String>> getOrderList(String doctorNum, String dateStr);
    List<OrderMessage> getNumberSequencebyId(int numberId);
    ReturnMessage addOrderMessage(OrderMessage orderMessage);
    //返回预约记录
    List<Map<String, String>> getOrderRecord(int userId);
    ReturnMessage returnNumber(int id);
    int setStatus(int status, int numberId);
}
