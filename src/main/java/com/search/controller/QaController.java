package com.search.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.search.common.dto.ResponseDto;
import com.search.common.es.QaSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class QaController {

    private static final Logger LOG = LoggerFactory.getLogger(QaController.class);

    @Autowired
    private QaSearch qaSearch;

    /**
     * 提交数据
     * @param body 请求体
     * @return
     */
    @PostMapping("/person")
    public ResponseDto submit(@RequestBody String body) {
        LOG.info("请求数据：" + body);

        JSONObject requestJson = JSON.parseObject(body);

        String name = requestJson.getString("name");
        String sex = requestJson.getString("sex");
        String interest = requestJson.getString("interest");

        qaSearch.submit(name, sex, interest);
        return new ResponseDto();
    }

    /**
     * 检索数据
     * @param request HttpServletRequest
     * @return 查询结果
     */
    @GetMapping("/qa/search")
    public ResponseDto<JSONArray> search(HttpServletRequest request) {
        Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
        Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
        String sex = request.getParameter("sex");
        String word = request.getParameter("name");
        pageNo = pageNo <= 0 ? 0 : pageNo - 1;
        LOG.info("[收到查询]pageNo:" + pageNo + ",pageSize:" + pageSize + ",sex:" + sex + ",word:" + word );
        JSONArray result = qaSearch.search(word, sex, pageNo, pageSize);
        return new ResponseDto(result);
    }



}
