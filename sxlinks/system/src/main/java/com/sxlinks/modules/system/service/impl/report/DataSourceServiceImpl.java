package com.sxlinks.modules.system.service.impl.report;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxlinks.common.util.JdbcConstants;

import com.sxlinks.modules.system.controller.report.constant.BaseOperationEnum;
import com.sxlinks.modules.system.controller.report.constant.Enabled;
import com.sxlinks.modules.system.controller.report.dataSource.code.ResponseCode;
import com.sxlinks.modules.system.controller.report.dataSource.constant.BusinessConstant;
import com.sxlinks.modules.system.controller.report.exception.BusinessException;
import com.sxlinks.modules.system.controller.report.exception.BusinessExceptionBuilder;
import com.sxlinks.modules.system.controller.report.param.ConnectionParam;
import com.sxlinks.modules.system.controller.report.utils.GaeaAssert;
import com.sxlinks.modules.system.entity.report.DataSetDto;
import com.sxlinks.modules.system.entity.report.DataSource;
import com.sxlinks.modules.system.entity.report.DataSourceDto;
import com.sxlinks.modules.system.mapper.report.DataSourceMapper;
import com.sxlinks.modules.system.service.report.DataSetParamService;
import com.sxlinks.modules.system.service.report.DataSourceService;
import com.sxlinks.modules.system.service.report.JdbcService;
import com.sxlinks.utils.HttpKit;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Raod
 * @desc DataSource 数据集服务实现
 * @date 2021-03-18 12:09:57.728203200
 **/
@Service
@Slf4j
public class DataSourceServiceImpl extends ServiceImpl<DataSourceMapper, DataSource> implements DataSourceService {

    @Autowired
    private DataSourceMapper dataSourceMapper;

//    @Resource(name = "dataSourceRestTemplate1")
//    private RestTemplate restTemplate1;

    @Autowired
    private DataSetParamService dataSetParamService;

    @Autowired
    private JdbcService jdbcService;


    /**
     * 获取所有数据源
     * @return
     */
    @Override
    public List<DataSource> queryAllDataSource() {
        LambdaQueryWrapper<DataSource> wrapper = Wrappers.lambdaQuery();
        wrapper.select(DataSource::getSourceCode, DataSource::getSourceName)
                .eq(DataSource::getEnableFlag, Enabled.YES.getValue());
        wrapper.orderByDesc(DataSource::getModifyTime);
        return dataSourceMapper.selectList(wrapper);
    }

    /**
     * 测试 连接
     *
     * @param connectionParam
     * @return
     */
    @Override
    public Boolean testConnection(ConnectionParam connectionParam) {
        boolean rstr=false;
        String sourceType = connectionParam.getSourceType();
        String sourceConfig = connectionParam.getSourceConfig();
        DataSourceDto dto = new DataSourceDto();
        dto.setSourceConfig(sourceConfig);
        switch (sourceType) {
            case JdbcConstants.ELASTIC_SEARCH_SQL:
                //testElasticsearchSqlConnection(dto);
                break;
            case JdbcConstants.MYSQL:
            case JdbcConstants.KUDU_IMAPLA:
            case JdbcConstants.ORACLE:
            case JdbcConstants.SQL_SERVER:
            case JdbcConstants.JDBC:
            case JdbcConstants.POSTGRESQL:
                rstr=testRelationalDb(dto);
                break;
            case JdbcConstants.HTTP:
                rstr=testHttp(dto);
                break;
            default:
                throw BusinessExceptionBuilder.build(ResponseCode.DATA_SOURCE_TYPE_DOES_NOT_MATCH_TEMPORARILY);
        }
        log.info("测试连接成功：{}", JSONObject.toJSONString(connectionParam));
        return rstr;

    }

    @Override
    public List<JSONObject> execute(DataSourceDto dto) {
        String sourceType = dto.getSourceType();
        switch (sourceType) {
//            case JdbcConstants.ELASTIC_SEARCH_SQL:
//                return executeElasticsearchSql(dto);
            case JdbcConstants.MYSQL:
            case JdbcConstants.KUDU_IMAPLA:
            case JdbcConstants.ORACLE:
            case JdbcConstants.SQL_SERVER:
            case JdbcConstants.JDBC:
            case JdbcConstants.POSTGRESQL:
                return executeRelationalDb(dto);
            case JdbcConstants.HTTP:
                return executeHttp(dto);
            default:
                throw BusinessExceptionBuilder.build(ResponseCode.DATA_SOURCE_TYPE_DOES_NOT_MATCH_TEMPORARILY);
        }

    }

//    /**
//     * 执行sql,统计数据total
//     *
//     * @param dto
//     * @return
//     */
    @Override
    public long total(DataSourceDto sourceDto, DataSetDto dto) {
        //区分数据类型
        String sourceType = sourceDto.getSourceType();
        switch (sourceType) {
            case JdbcConstants.ELASTIC_SEARCH_SQL:
                return 0;
            case JdbcConstants.MYSQL:
                return mysqlTotal(sourceDto, dto);
            default:
                throw BusinessExceptionBuilder.build(ResponseCode.DATA_SOURCE_TYPE_DOES_NOT_MATCH_TEMPORARILY);
        }
    }
//
//    /**
//     * 获取mysql count 和添加limit分页信息
//     * @param sourceDto
//     * @param dto
//     * @return
//     */
    public long mysqlTotal(DataSourceDto sourceDto, DataSetDto dto){
        String dynSentence = sourceDto.getDynSentence();
        String sql = "select count(1) as count from (" + dynSentence + ") as gaeaExecute";
        sourceDto.setDynSentence(sql);
        List<JSONObject> result = execute(sourceDto);

        //sql 拼接 limit 分页信息
        int pageNumber = Integer.parseInt(dto.getContextData().getOrDefault("pageNumber", "1").toString());
        int pageSize = Integer.parseInt(dto.getContextData().getOrDefault("pageSize", "10").toString());
        String sqlLimit = " limit " + (pageNumber - 1) * pageSize + "," + pageSize;
        sourceDto.setDynSentence(dynSentence.concat(sqlLimit));
        log.info("当前total：{}, 添加分页参数,sql语句：{}", JSONObject.toJSONString(result), sourceDto.getDynSentence());
        return result.get(0).getLongValue("count");
    }
//
//
//
//    public List<JSONObject> executeElasticsearchSql(DataSourceDto dto) {
//        analysisHttpConfig(dto);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAll(JSONObject.parseObject(dto.getHeader(), Map.class));
//        HttpEntity<String> entity = new HttpEntity<>(dto.getDynSentence(), headers);
//        ResponseEntity<JSONObject> exchange;
//        try {
//            exchange = restTemplate.exchange(dto.getApiUrl(), HttpMethod.valueOf(dto.getMethod()), entity, JSONObject.class);
//        } catch (Exception e) {
//            log.error("error",e);
//            throw BusinessExceptionBuilder.build(ResponseCode.DATA_SOURCE_CONNECTION_FAILED, e.getMessage());
//        }
//        if (exchange.getStatusCode().isError()) {
//            throw BusinessExceptionBuilder.build(ResponseCode.DATA_SOURCE_CONNECTION_FAILED, exchange.getBody());
//        }
//        List<JSONObject> result;
//        try {
//            JSONObject body = exchange.getBody();
//            //解析es sql数据
//            if (null == body) {
//                return null;
//            }
//            JSONArray columns = body.getJSONArray("columns");
//            JSONArray rows = body.getJSONArray("rows");
//            result = new ArrayList<>();
//            for (int i = 0; i < rows.size(); i++) {
//                JSONArray row = rows.getJSONArray(i);
//                JSONObject jsonObject = new JSONObject();
//                for (int j = 0; j < row.size(); j++) {
//                    String name = columns.getJSONObject(j).getString("name");
//                    String value = row.getString(j);
//                    jsonObject.put(name, value);
//                }
//                result.add(jsonObject);
//            }
//        } catch (Exception e) {
//            log.error("error",e);
//            throw BusinessExceptionBuilder.build(ResponseCode.ANALYSIS_DATA_ERROR, e.getMessage());
//        }
//        return result;
//    }
//

    public List<JSONObject> executeRelationalDb(DataSourceDto dto) {
        analysisRelationalDbConfig(dto);
        Connection pooledConnection = null;
        try {
            pooledConnection = jdbcService.getPooledConnection(dto);

            PreparedStatement statement = pooledConnection.prepareStatement(dto.getDynSentence());
            ResultSet rs = statement.executeQuery();

            int columnCount = rs.getMetaData().getColumnCount();

            List<String> columns = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rs.getMetaData().getColumnLabel(i);
                columns.add(columnName);
            }
            List<JSONObject> list = new ArrayList<>();
            while (rs.next()) {
                JSONObject jo = new JSONObject();
                columns.forEach(t -> {
                    try {
                        Object value = rs.getObject(t);
                        //数据类型转换
                        Object result = dealResult(value);
                        jo.put(t, result);
                    } catch (SQLException throwable) {
                        log.error("error",throwable);
                        throw BusinessExceptionBuilder.build(ResponseCode.EXECUTE_SQL_ERROR, throwable.getMessage());
                    }
                });
                list.add(jo);
            }
            return list;
        } catch (Exception throwable) {
            log.error("error",throwable);
            throw BusinessExceptionBuilder.build(ResponseCode.EXECUTE_SQL_ERROR, throwable.getMessage());
        } finally {
            try {
                if (pooledConnection != null) {
                    pooledConnection.close();
                }
            } catch (SQLException throwable) {
                log.error("error",throwable);
                throw BusinessExceptionBuilder.build(ResponseCode.DATA_SOURCE_CONNECTION_FAILED, throwable.getMessage());
            }
        }
    }
//    /**
//     * 解决sql返回值 类型问题
//     * (through reference chain: java.util.HashMap["pageData"]->java.util.ArrayList[0]->java.util.HashMap["UPDATE_TIME"]->oracle.sql.TIMESTAMP["stream"])
//     * @param result
//     * @return
//     * @throws SQLException
//     */
    private Object dealResult(Object result) throws SQLException {
        if (null == result) {
            return result;
        }
        String type = result.getClass().getName();
        if ("oracle.sql.TIMESTAMP".equals(type)) {
            //oracle.sql.TIMESTAMP处理逻辑
            return new Date((Long) JSONObject.toJSON(result));
        }

        return result;
    }

    /**
     * http 执行获取数据
     *
     * @param dto
     */
    public List<JSONObject> executeHttp(DataSourceDto dto) {
        String rstr="";
        analysisHttpConfig(dto);
        //HttpHeaders headers = new HttpHeaders();
//        headers.setAll(JSONObject.parseObject(dto.getHeader(), Map.class));
//        HttpEntity<String> entity = new HttpEntity<>(dto.getDynSentence(), headers);
//        ResponseEntity<Object> exchange;

        if(dto.getMethod().toLowerCase().equals("get")){
            Unirest.config().verifySsl(false);//不验证ssl
            HttpResponse<String> response = Unirest.get(dto.getApiUrl())
                    .headers(JSONObject.parseObject(dto.getHeader(), Map.class))
                    .queryString(JSONObject.parseObject(dto.getBody(), Map.class)) //映射参数
                    .asString();
            if(response.getStatus()==200){
                rstr=response.getBody();
            }else{

            }
        }
        if(dto.getMethod().toLowerCase().equals("post")){
            Unirest.config().verifySsl(false);//不验证ssl
            HttpResponse<String> response = Unirest.post(dto.getApiUrl())
                    .headers(JSONObject.parseObject(dto.getHeader(), Map.class))
                    .body(JSONObject.parseObject(dto.getBody(), Map.class)) //映射参数
                    .asString();
            if(response.getStatus()==200){
                rstr=response.getBody();
            }else{

            }
        }

        Object body = rstr;
        String jsonStr = JSONObject.toJSONString(body);
        List<JSONObject> result = new ArrayList<>();
        if (jsonStr.trim().startsWith(BusinessConstant.LEFT_BIG_BOAST) && jsonStr.trim().endsWith(BusinessConstant.RIGTH_BIG_BOAST)) {
            //JSONObject
            result.add(JSONObject.parseObject(jsonStr));
        } else if (jsonStr.trim().startsWith(BusinessConstant.LEFT_MIDDLE_BOAST) && jsonStr.trim().endsWith(BusinessConstant.RIGHT_MIDDLE_BOAST)) {
            //List
            result = JSONArray.parseArray(jsonStr, JSONObject.class);
        } else {
            result.add(new JSONObject());
        }
        return result;
    }

    /**
     * 关系型数据库 测试连接
     *
     * @param dto
     */
    public boolean testRelationalDb(DataSourceDto dto) {
        boolean rstr=false;
        analysisRelationalDbConfig(dto);
        try {
            Connection unPooledConnection = jdbcService.getUnPooledConnection(dto);
            String catalog = unPooledConnection.getCatalog();
            log.info("数据库测试连接成功：{}", catalog);
            rstr=true;
            unPooledConnection.close();
        } catch (SQLException e) {
            rstr=false;
            log.error("error",e);
            if (e.getCause() instanceof ClassNotFoundException) {
                throw BusinessExceptionBuilder.build(ResponseCode.CLASS_NOT_FOUND, e.getCause().getMessage());
            } else {
                throw BusinessExceptionBuilder.build(ResponseCode.DATA_SOURCE_CONNECTION_FAILED, e.getMessage());
            }

        }
        return rstr;
    }

    /**
     * http 测试连接
     *
     * @param dto
     */
    public boolean testHttp(DataSourceDto dto) {
        boolean rstr1=false;
        String rstr="";
        analysisHttpConfig(dto);
        String apiUrl = dto.getApiUrl();
        String method = dto.getMethod();
        String body = dto.getBody();

        try {
            if(dto.getMethod().toLowerCase().equals("get")){
                Unirest.config().verifySsl(false);//不验证ssl
                HttpResponse<String> response = Unirest.get(dto.getApiUrl())
                        .headers(JSONObject.parseObject(dto.getHeader(), Map.class))
                        .queryString(JSONObject.parseObject(dto.getBody(), Map.class)) //映射参数
                        .asString();
                rstr=String.valueOf(response.getStatus());

            }
            if(dto.getMethod().toLowerCase().equals("post")){
                Unirest.config().verifySsl(false);//不验证ssl
                HttpResponse<String> response = Unirest.post(dto.getApiUrl())
                        .headers(JSONObject.parseObject(dto.getHeader(), Map.class))
                        .body(JSONObject.parseObject(dto.getBody(), Map.class)) //映射参数
                        .asString();
                rstr=String.valueOf(response.getStatus());
            }

            if (!rstr.equals("200")) {
                rstr1=false;
            }else{
                rstr1=true;
            }
        } catch (Exception e) {
            rstr1=false;
        }
        return rstr1;
    }


    /**
     * 关系型数据库 测试连接
     *
     * @param dto
     */
//    public void testElasticsearchSqlConnection(DataSourceDto dto) {
//        analysisHttpConfig(dto);
//        String apiUrl = dto.getApiUrl();
//        String method = dto.getMethod();
//        String body = dto.getBody();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAll(JSONObject.parseObject(dto.getHeader(), Map.class));
//        HttpEntity<String> entity = new HttpEntity<>(body, headers);
//        ResponseEntity<Object> exchange;
//        try {
//            exchange = restTemplate1.exchange(apiUrl, HttpMethod.valueOf(method), entity, Object.class);
//            if (exchange.getStatusCode().isError()) {
//                throw BusinessExceptionBuilder.build(ResponseCode.DATA_SOURCE_CONNECTION_FAILED, exchange.getBody());
//            }
//        } catch (RestClientException e) {
//            throw BusinessExceptionBuilder.build(ResponseCode.DATA_SOURCE_CONNECTION_FAILED, e.getMessage());
//        }
//
//    }


    public void analysisRelationalDbConfig(DataSourceDto dto) {
        JSONObject json = JSONObject.parseObject(dto.getSourceConfig());
//        GaeaAssert.isFalse(json.containsKey("jdbcUrl"), ResponseCode.PARAM_IS_NULL,"jdbcUrl not empty");
//        GaeaAssert.isFalse(json.containsKey("driverName"), ResponseCode.PARAM_IS_NULL,"driverName not empty");
        String jdbcUrl = json.getString("jdbcUrl");
        String username = json.getString("username");
        String password = json.getString("password");
        String driverName = json.getString("driverName");
        dto.setJdbcUrl(jdbcUrl);
        dto.setDriverName(driverName);
        dto.setUsername(username);
        dto.setPassword(password);
    }


    /**
     * es通过api获取数据
     *
     * @param dto
     * @return
     */
    public void analysisHttpConfig(DataSourceDto dto) {
        JSONObject json = JSONObject.parseObject(dto.getSourceConfig());
        GaeaAssert.isFalse(json.containsKey("apiUrl"), ResponseCode.PARAM_IS_NULL,"apiUrl not empty");
        GaeaAssert.isFalse(json.containsKey("method"), ResponseCode.PARAM_IS_NULL,"method not empty");
        GaeaAssert.isFalse(json.containsKey("header"), ResponseCode.PARAM_IS_NULL,"header not empty");
        GaeaAssert.isFalse(json.containsKey("body"), ResponseCode.PARAM_IS_NULL,"body not empty");
        String apiUrl = json.getString("apiUrl");
        String method = json.getString("method");
        String header = json.getString("header");
        String body = json.getString("body");
        //解决url中存在的动态参数
        apiUrl = dataSetParamService.transform(dto.getContextData(), apiUrl);
        //请求头中动态参数
        header = dataSetParamService.transform(dto.getContextData(), header);
        dto.setApiUrl(apiUrl);
        dto.setMethod(method);
        dto.setHeader(header);
        dto.setBody(body);
    }

    /**
     * 操作后续处理
     *
     * @param entity
     * @param operationEnum 操作类型
     * @throws BusinessException 阻止程序继续执行或回滚事务
     */
//    @Override
//    public void processAfterOperation(DataSource entity, BaseOperationEnum operationEnum) throws BusinessException {
//        jdbcService.removeJdbcConnectionPool(entity.getId());
//    }
}
