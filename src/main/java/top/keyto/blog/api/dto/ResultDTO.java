package top.keyto.blog.api.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

/**
 * @author Keyto
 * Created on 2019/11/21
 */
@Slf4j
@Data
public class ResultDTO<T> {
    private String code; // 成功或者失败的code错误码
    private T data; // 成功时返回的数据，失败时返回具体的异常信息
    private Object msg; // 请求失败返回的提示信息
    private Timestamp currentTime; // 服务器当前时间

    public static <S> ResultDTO<S> OK(S data) {
        ResultDTO<S> resultDTO = new ResultDTO<>();
        resultDTO.setCode("0");
        resultDTO.setData(data);
        return resultDTO;
    }

    public static <S> ResultDTO<S> FAILE(String code, String msg, S data) {
        ResultDTO<S> resultDTO = new ResultDTO<>();
        resultDTO.setCode(code);
        resultDTO.setMsg(msg);
        resultDTO.setData(data);
        if (log.isDebugEnabled()) {
            resultDTO.setCurrentTime(new Timestamp(System.currentTimeMillis()));
        }
        return resultDTO;
    }
}
