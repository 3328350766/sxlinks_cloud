package com.sxlinks.bean.vo;


import java.util.Date;


public class TelephoneRuleVO {

    //规则基本信息
    private Long id;    //编号
    private String name;    //名称
    private Long websiteId;
    private Long websiteColumnId;
    private String description; //描述
    private String type;    //采集类型
    private String discernType; //识别类型
    private String state;
    private String needDownloadImage;   //是否需要下载远程图片
    private String localImagePath;       //本地图片保存路径
    private String urlImagePath;    //图片URL访问地址
    private String islive;      //是否存活
    private Date lastCheckLiveTime;     //检测存活时间
    private Double prSpider;
    private Long ruleListId;   //列表规则ID
    private Long ruleInfoId;    //信息规则ID
    private Long poolTacticId;  //池策略ID
    private String generateBy;  //生成方式
    private String belong;      //归属
    private String listUrl; //列表地址
    private String infoUrl;     //信息地址
    private Integer lsPageStart;    //启始数
    private Integer lsPageEnd;  //结束数

    //列表规则
    private String listRuleName;    //列表规则名称
    private String listRegular;     //列表规则
    private String listPageType;    //列表页面列类型
    private String listNeedPaging;  //列表是否需要分页
    private String listPagingRegular;   //列表分页规则
    private String listEncoding;    //列表页面编码
    private String listDiscernType; //列表识别类型
    private String listUrlType;    //列表类型
    private String listDownloader;  //下载引擎

    //基本信息
    private String infoRuleName;    //信息规则名称
    private String nameRegular;    //名称
    private String numRegular;    //号码
    private String numAttachmentRegular;    //号码附加规则
    private String numAttr;    //号码属性
    private String numAttachmentAttr;    //号码附加规则属性
    private String numIsReplace;    //号码是否替换
    private String addressRegular;     //地址
    private String descriptionRegular; //描述
    private String categoryRegular;    //行业分类
    private String timeRegular;    //时间规则
    private String provinceRegular;    //省
    private String cityRegular;    //市
    private String infoNeedPaging;//是否需要分页
    private String infoPagingRegular;//分页规则
    private String infoPageType;
    private String infoEncoding;
    private String infoDownloader;  //下载引擎

    //池策略
    private String poolTacticName;
    private String poolTacticSpiderType;
    private String poolTacticShellTactic;
    private String poolTacticShellCount;
    private String poolTacticShellTime;
    //池帐号
    private String poolTacticAccountUsername;
    private String poolTacticAccountPassword;
    private String poolTacticAccountCookie;
    //池IP
    private String poolTacticIp;
    private String poolTacticPort;
    private String poolTacticProto;
    private String poolTacticIpUsername;
    private String poolTacticIpPassword;

    //多关联2个-预留
    private Long spiderNodeId;          //采集节点ID
    private Long spiderMachineId;    //采集器ID

    /**两个附加属性**/
    String ruleInfoName;
    String listName;

    /**两个关联属性**/
    String listid;
    String infoid;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumAttachmentRegular() {
        return numAttachmentRegular;
    }

    public void setNumAttachmentRegular(String numAttachmentRegular) {
        this.numAttachmentRegular = numAttachmentRegular;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Long websiteId) {
        this.websiteId = websiteId;
    }

    public Long getWebsiteColumnId() {
        return websiteColumnId;
    }

    public void setWebsiteColumnId(Long websiteColumnId) {
        this.websiteColumnId = websiteColumnId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscernType() {
        return discernType;
    }

    public void setDiscernType(String discernType) {
        this.discernType = discernType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNeedDownloadImage() {
        return needDownloadImage;
    }

    public void setNeedDownloadImage(String needDownloadImage) {
        this.needDownloadImage = needDownloadImage;
    }

    public String getLocalImagePath() {
        return localImagePath;
    }

    public void setLocalImagePath(String localImagePath) {
        this.localImagePath = localImagePath;
    }

    public String getUrlImagePath() {
        return urlImagePath;
    }

    public void setUrlImagePath(String urlImagePath) {
        this.urlImagePath = urlImagePath;
    }

    public String getIslive() {
        return islive;
    }

    public void setIslive(String islive) {
        this.islive = islive;
    }

    public Date getLastCheckLiveTime() {
        return lastCheckLiveTime;
    }

    public void setLastCheckLiveTime(Date lastCheckLiveTime) {
        this.lastCheckLiveTime = lastCheckLiveTime;
    }

    public Double getPrSpider() {
        return prSpider;
    }

    public void setPrSpider(Double prSpider) {
        this.prSpider = prSpider;
    }

    public Long getRuleListId() {
        return ruleListId;
    }

    public void setRuleListId(Long ruleListId) {
        this.ruleListId = ruleListId;
    }

    public Long getRuleInfoId() {
        return ruleInfoId;
    }

    public void setRuleInfoId(Long ruleInfoId) {
        this.ruleInfoId = ruleInfoId;
    }

    public Long getPoolTacticId() {
        return poolTacticId;
    }

    public void setPoolTacticId(Long poolTacticId) {
        this.poolTacticId = poolTacticId;
    }

    public String getGenerateBy() {
        return generateBy;
    }

    public void setGenerateBy(String generateBy) {
        this.generateBy = generateBy;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getListUrl() {
        return listUrl;
    }

    public void setListUrl(String listUrl) {
        this.listUrl = listUrl;
    }

    public String getListRuleName() {
        return listRuleName;
    }

    public void setListRuleName(String listRuleName) {
        this.listRuleName = listRuleName;
    }

    public Integer getLsPageStart() {
        return lsPageStart;
    }

    public void setLsPageStart(Integer lsPageStart) {
        this.lsPageStart = lsPageStart;
    }

    public Integer getLsPageEnd() {
        return lsPageEnd;
    }

    public void setLsPageEnd(Integer lsPageEnd) {
        this.lsPageEnd = lsPageEnd;
    }

    public String getListRegular() {
        return listRegular;
    }

    public void setListRegular(String listRegular) {
        this.listRegular = listRegular;
    }

    public String getListPageType() {
        return listPageType;
    }

    public void setListPageType(String listPageType) {
        this.listPageType = listPageType;
    }

    public String getListNeedPaging() {
        return listNeedPaging;
    }

    public void setListNeedPaging(String listNeedPaging) {
        this.listNeedPaging = listNeedPaging;
    }

    public String getListPagingRegular() {
        return listPagingRegular;
    }

    public void setListPagingRegular(String listPagingRegular) {
        this.listPagingRegular = listPagingRegular;
    }

    public String getListEncoding() {
        return listEncoding;
    }

    public void setListEncoding(String listEncoding) {
        this.listEncoding = listEncoding;
    }

    public String getListDiscernType() {
        return listDiscernType;
    }

    public void setListDiscernType(String listDiscernType) {
        this.listDiscernType = listDiscernType;
    }

    public String getListUrlType() {
        return listUrlType;
    }

    public void setListUrlType(String listUrlType) {
        this.listUrlType = listUrlType;
    }

    public String getListDownloader() {
        return listDownloader;
    }

    public void setListDownloader(String listDownloader) {
        this.listDownloader = listDownloader;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }
    

    public String getInfoRuleName() {
        return infoRuleName;
    }

    public void setInfoRuleName(String infoRuleName) {
        this.infoRuleName = infoRuleName;
    }

    public String getNameRegular() {
        return nameRegular;
    }

    public void setNameRegular(String nameRegular) {
        this.nameRegular = nameRegular;
    }

    public String getNumRegular() {
        return numRegular;
    }

    public void setNumRegular(String numRegular) {
        this.numRegular = numRegular;
    }

    public String getAddressRegular() {
        return addressRegular;
    }

    public void setAddressRegular(String addressRegular) {
        this.addressRegular = addressRegular;
    }

    public String getDescriptionRegular() {
        return descriptionRegular;
    }

    public void setDescriptionRegular(String descriptionRegular) {
        this.descriptionRegular = descriptionRegular;
    }

    public String getCategoryRegular() {
        return categoryRegular;
    }

    public void setCategoryRegular(String categoryRegular) {
        this.categoryRegular = categoryRegular;
    }

    public String getTimeRegular() {
        return timeRegular;
    }

    public void setTimeRegular(String timeRegular) {
        this.timeRegular = timeRegular;
    }

    public String getProvinceRegular() {
        return provinceRegular;
    }

    public void setProvinceRegular(String provinceRegular) {
        this.provinceRegular = provinceRegular;
    }

    public String getCityRegular() {
        return cityRegular;
    }

    public void setCityRegular(String cityRegular) {
        this.cityRegular = cityRegular;
    }

    public String getInfoNeedPaging() {
        return infoNeedPaging;
    }

    public void setInfoNeedPaging(String infoNeedPaging) {
        this.infoNeedPaging = infoNeedPaging;
    }

    public String getInfoPagingRegular() {
        return infoPagingRegular;
    }

    public void setInfoPagingRegular(String infoPagingRegular) {
        this.infoPagingRegular = infoPagingRegular;
    }

    public String getInfoEncoding() {
        return infoEncoding;
    }

    public void setInfoEncoding(String infoEncoding) {
        this.infoEncoding = infoEncoding;
    }

    public String getInfoDownloader() {
        return infoDownloader;
    }

    public void setInfoDownloader(String infoDownloader) {
        this.infoDownloader = infoDownloader;
    }

    public String getPoolTacticName() {
        return poolTacticName;
    }

    public void setPoolTacticName(String poolTacticName) {
        this.poolTacticName = poolTacticName;
    }

    public String getPoolTacticSpiderType() {
        return poolTacticSpiderType;
    }

    public void setPoolTacticSpiderType(String poolTacticSpiderType) {
        this.poolTacticSpiderType = poolTacticSpiderType;
    }

    public String getPoolTacticShellTactic() {
        return poolTacticShellTactic;
    }

    public void setPoolTacticShellTactic(String poolTacticShellTactic) {
        this.poolTacticShellTactic = poolTacticShellTactic;
    }

    public String getPoolTacticShellCount() {
        return poolTacticShellCount;
    }

    public void setPoolTacticShellCount(String poolTacticShellCount) {
        this.poolTacticShellCount = poolTacticShellCount;
    }

    public String getPoolTacticShellTime() {
        return poolTacticShellTime;
    }

    public void setPoolTacticShellTime(String poolTacticShellTime) {
        this.poolTacticShellTime = poolTacticShellTime;
    }

    public String getPoolTacticAccountUsername() {
        return poolTacticAccountUsername;
    }

    public void setPoolTacticAccountUsername(String poolTacticAccountUsername) {
        this.poolTacticAccountUsername = poolTacticAccountUsername;
    }

    public String getPoolTacticAccountPassword() {
        return poolTacticAccountPassword;
    }

    public void setPoolTacticAccountPassword(String poolTacticAccountPassword) {
        this.poolTacticAccountPassword = poolTacticAccountPassword;
    }

    public String getPoolTacticAccountCookie() {
        return poolTacticAccountCookie;
    }

    public void setPoolTacticAccountCookie(String poolTacticAccountCookie) {
        this.poolTacticAccountCookie = poolTacticAccountCookie;
    }

    public String getPoolTacticIp() {
        return poolTacticIp;
    }

    public void setPoolTacticIp(String poolTacticIp) {
        this.poolTacticIp = poolTacticIp;
    }

    public String getPoolTacticPort() {
        return poolTacticPort;
    }

    public void setPoolTacticPort(String poolTacticPort) {
        this.poolTacticPort = poolTacticPort;
    }

    public String getPoolTacticProto() {
        return poolTacticProto;
    }

    public void setPoolTacticProto(String poolTacticProto) {
        this.poolTacticProto = poolTacticProto;
    }

    public String getPoolTacticIpUsername() {
        return poolTacticIpUsername;
    }

    public void setPoolTacticIpUsername(String poolTacticIpUsername) {
        this.poolTacticIpUsername = poolTacticIpUsername;
    }

    public String getPoolTacticIpPassword() {
        return poolTacticIpPassword;
    }

    public void setPoolTacticIpPassword(String poolTacticIpPassword) {
        this.poolTacticIpPassword = poolTacticIpPassword;
    }

    public Long getSpiderNodeId() {
        return spiderNodeId;
    }

    public void setSpiderNodeId(Long spiderNodeId) {
        this.spiderNodeId = spiderNodeId;
    }

    public Long getSpiderMachineId() {
        return spiderMachineId;
    }

    public void setSpiderMachineId(Long spiderMachineId) {
        this.spiderMachineId = spiderMachineId;
    }

    public String getNumAttr() {
        return numAttr;
    }

    public void setNumAttr(String numAttr) {
        this.numAttr = numAttr;
    }

    public String getNumAttachmentAttr() {
        return numAttachmentAttr;
    }

    public void setNumAttachmentAttr(String numAttachmentAttr) {
        this.numAttachmentAttr = numAttachmentAttr;
    }

    public String getNumIsReplace() {
        return numIsReplace;
    }

    public void setNumIsReplace(String numIsReplace) {
        this.numIsReplace = numIsReplace;
    }

    public String getRuleInfoName() {
        return ruleInfoName;
    }

    public void setRuleInfoName(String ruleInfoName) {
        this.ruleInfoName = ruleInfoName;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListid() {
        return listid;
    }

    public void setListid(String listid) {
        this.listid = listid;
    }

    public String getInfoid() {
        return infoid;
    }

    public void setInfoid(String infoid) {
        this.infoid = infoid;
    }

    public String getInfoPageType() {
        return infoPageType;
    }

    public void setInfoPageType(String infoPageType) {
        this.infoPageType = infoPageType;
    }
}
