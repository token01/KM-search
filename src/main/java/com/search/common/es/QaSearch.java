package com.search.common.es;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class QaSearch {

    private static final Logger LOG = LoggerFactory.getLogger(QaSearch.class);

    /**
     * 文档类型
     */
    private final static String TYPE = "yingle";

    @Autowired
    private ElasticSearchClient client;

    /**
     * 保存数据到es
     * @param name 姓名
     * @param sex 性别
     * @param interest 爱好
     */
    public void submit(String name, String sex, String interest) {
        JSONObject jsonData = new JSONObject();
        jsonData.put("name", name);
        jsonData.put("sex", sex);
        jsonData.put("date", Calendar.getInstance().getTimeInMillis() / 1000);
        client.createIndex(TYPE, jsonData);
    }

    /**
     * 检索
     * @param pageNo 页码
     * @param pageSize 当页显示数据量
     * @return 查询结果集
     */
    public JSONArray search(String word, String sex, int pageNo, int pageSize) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if(!StringUtils.isEmpty(word)) {
//            queryBuilder.must(QueryBuilders.matchPhraseQuery("word", word));
            QueryBuilders.multiMatchQuery( word, "subject", "detail");
            List<String> highlightList = new ArrayList<String>();
            highlightList.add("word");
            return client.searchHighlight(queryBuilder,word,TYPE, highlightList , pageNo, pageSize);
        }
        return client.search(queryBuilder, TYPE, pageNo, pageSize);
    }

}
