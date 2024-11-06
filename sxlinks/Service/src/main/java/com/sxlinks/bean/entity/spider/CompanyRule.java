package com.sxlinks.bean.entity.spider;

import lombok.Data;
import org.hibernate.annotations.Proxy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "company_rule")
@Data
@EntityListeners(AuditingEntityListener.class)
@Proxy(lazy = false)
public class CompanyRule {
    @Id
    @GeneratedValue
    private Long id;    //编号
    @Column
    private String name;    //名称
    @Column
    private String description; //描述
    @Column
    private String type;    //采集类型
    @Column(name="discern_type")
    private String discernType; //识别类型
    @Column(name="need_paging")
    private Integer needPaging; //是否需要分页
    @Column(name="paging_regular")
    private String pagingRegular;   //分页规则
    @Column(name="name_regular")
    private String nameRegular;    //标题规则
    @Column(name="address_regular")
    private String addressRegular;  //内容规则
    @Column(name="website_regular")
    private String websiteRegular;     //时间规则
    @Column(name="telephone_regular")
    private String telephoneRegular;   //作者规则
    @Column(name="mobilephone_regular")
    private String mobilephoneRegular;   //附件规则
    @Column(name="list_url")
    private String listUrl; //列表地址
    @Column(name="list_url_type")
    private String listUrlType; //列表类型
    @Column(name="list_discern_type")
    private String listDiscernType; //列表识别类型
    @Column(name="list_regular")
    private String listRegular; //列表规则
    @Column(name="list_need_paging")
    private String listNeedPaging;  //列表是否需要分页
    @Column(name="ls_page_start")
    private String lsPageStart;  //分页起始页
    @Column(name="ls_page_end")
    private String lsPageEnd;  //分页结束页
    @Column(name="list_paging_regular")
    private String listPagingRegular;   //列表分页规则
    @Column(name="state")
    private String state;
    @Column(name="info_url")
    private String infoUrl;     //信息地址
    @Column(name="need_download_image")
    private String needDownloadImage;   //是否需要下载远程图片
    @Column(name="local_image_path")
    private String localImagePath;       //本地图片保存路径
    @Column(name="url_image_path")
    private String urlImagePath;    //图片URL访问地址
    @Column(name="info_replace_regular")
    private String infoReplaceRegular;  //信息替换规则
    @Column(name="list_downloader")
    private String listDownloader;    //列表下载器
    @Column(name="info_downloader")
    private String infoDownloader;  //信息下载器
    @Column(name="list_encoding")
    private String listEncoding;    //列表编码
    @Column(name="info_encoding")
    private String infoEncoding;  //信息编码
    @Column(name="islive")
    private String islive;     //存活状态
    @Column(name="last_check_live_time")
    private Date lastCheckLiveTime;     //检测存活时间

    @Column(name="website2_regular")
    private String website2Regular;
    @Column(name="fax_regular")
    private String faxRegular;
    @Column(name="country_regular")
    private String countryRegular;
    @Column(name="province_regular")
    private String provinceRegular;
    @Column(name="city_regular")
    private String cityRegular;
    @Column(name="email_regular")
    private String emailRegular;

    @Column(name="code_regular")
    private String codeRegular;    //社会代码
    @Column(name="type_regular")
    private String typeRegular;    //公司类型
    @Column(name="faren_regular")
    private String farenRegular;   //法人
    @Column(name="ziben_regular")
    private String zibenRegular;   //注册资本
    @Column(name="kaiye_starttime_regular")
    private String kaiyeStarttimeRegular; //营业开始时间
    @Column(name="kaiye_endtime_regular")
    private String kaiyeEndtimeRegular;   //营业结束时间
    @Column(name="dengji_jiguan_regular")
    private String dengjiJiguanRegular;   //登记机关
    @Column(name="hezun_time_regular")
    private String hezunTimeRegular;  //核准时间
    @Column(name="dengji_state_regular")
    private String dengjiStateRegular;    //登记状态
    @Column(name="scope_regular")
    private String scopeRegular;   //经营范围

    @Column(name = "create_time")
    private Date createTime;    //创建时间
    @Column(name = "create_by")
    private Long createBy;  //创建人
    @Column(name = "modify_time")
    private Date modifyTime;    //修改时间
    @Column(name = "modify_by")
    private Long modifyBy;  //修改人

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getNeedPaging() {
        return needPaging;
    }

    public void setNeedPaging(Integer needPaging) {
        this.needPaging = needPaging;
    }

    public String getPagingRegular() {
        return pagingRegular;
    }

    public void setPagingRegular(String pagingRegular) {
        this.pagingRegular = pagingRegular;
    }

    public String getNameRegular() {
        return nameRegular;
    }

    public void setNameRegular(String nameRegular) {
        this.nameRegular = nameRegular;
    }

    public String getAddressRegular() {
        return addressRegular;
    }

    public void setAddressRegular(String addressRegular) {
        this.addressRegular = addressRegular;
    }

    public String getWebsiteRegular() {
        return websiteRegular;
    }

    public void setWebsiteRegular(String websiteRegular) {
        this.websiteRegular = websiteRegular;
    }

    public String getTelephoneRegular() {
        return telephoneRegular;
    }

    public void setTelephoneRegular(String telephoneRegular) {
        this.telephoneRegular = telephoneRegular;
    }

    public String getMobilephoneRegular() {
        return mobilephoneRegular;
    }

    public void setMobilephoneRegular(String mobilephoneRegular) {
        this.mobilephoneRegular = mobilephoneRegular;
    }

    public String getListUrl() {
        return listUrl;
    }

    public void setListUrl(String listUrl) {
        this.listUrl = listUrl;
    }

    public String getListUrlType() {
        return listUrlType;
    }

    public void setListUrlType(String listUrlType) {
        this.listUrlType = listUrlType;
    }

    public String getListDiscernType() {
        return listDiscernType;
    }

    public void setListDiscernType(String listDiscernType) {
        this.listDiscernType = listDiscernType;
    }

    public String getListRegular() {
        return listRegular;
    }

    public void setListRegular(String listRegular) {
        this.listRegular = listRegular;
    }

    public String getListNeedPaging() {
        return listNeedPaging;
    }

    public void setListNeedPaging(String listNeedPaging) {
        this.listNeedPaging = listNeedPaging;
    }

    public String getLsPageStart() {
        return lsPageStart;
    }

    public void setLsPageStart(String lsPageStart) {
        this.lsPageStart = lsPageStart;
    }

    public String getLsPageEnd() {
        return lsPageEnd;
    }

    public void setLsPageEnd(String lsPageEnd) {
        this.lsPageEnd = lsPageEnd;
    }

    public String getListPagingRegular() {
        return listPagingRegular;
    }

    public void setListPagingRegular(String listPagingRegular) {
        this.listPagingRegular = listPagingRegular;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
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

    public String getInfoReplaceRegular() {
        return infoReplaceRegular;
    }

    public void setInfoReplaceRegular(String infoReplaceRegular) {
        this.infoReplaceRegular = infoReplaceRegular;
    }

    public String getListDownloader() {
        return listDownloader;
    }

    public void setListDownloader(String listDownloader) {
        this.listDownloader = listDownloader;
    }

    public String getInfoDownloader() {
        return infoDownloader;
    }

    public void setInfoDownloader(String infoDownloader) {
        this.infoDownloader = infoDownloader;
    }

    public String getListEncoding() {
        return listEncoding;
    }

    public void setListEncoding(String listEncoding) {
        this.listEncoding = listEncoding;
    }

    public String getInfoEncoding() {
        return infoEncoding;
    }

    public void setInfoEncoding(String infoEncoding) {
        this.infoEncoding = infoEncoding;
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

    public String getWebsite2Regular() {
        return website2Regular;
    }

    public void setWebsite2Regular(String website2Regular) {
        this.website2Regular = website2Regular;
    }

    public String getFaxRegular() {
        return faxRegular;
    }

    public void setFaxRegular(String faxRegular) {
        this.faxRegular = faxRegular;
    }

    public String getCountryRegular() {
        return countryRegular;
    }

    public void setCountryRegular(String countryRegular) {
        this.countryRegular = countryRegular;
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

    public String getEmailRegular() {
        return emailRegular;
    }

    public void setEmailRegular(String emailRegular) {
        this.emailRegular = emailRegular;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Long modifyBy) {
        this.modifyBy = modifyBy;
    }

    public String getCodeRegular() {
        return codeRegular;
    }

    public void setCodeRegular(String codeRegular) {
        this.codeRegular = codeRegular;
    }

    public String getTypeRegular() {
        return typeRegular;
    }

    public void setTypeRegular(String typeRegular) {
        this.typeRegular = typeRegular;
    }

    public String getFarenRegular() {
        return farenRegular;
    }

    public void setFarenRegular(String farenRegular) {
        this.farenRegular = farenRegular;
    }

    public String getZibenRegular() {
        return zibenRegular;
    }

    public void setZibenRegular(String zibenRegular) {
        this.zibenRegular = zibenRegular;
    }

    public String getKaiyeStarttimeRegular() {
        return kaiyeStarttimeRegular;
    }

    public void setKaiyeStarttimeRegular(String kaiyeStarttimeRegular) {
        this.kaiyeStarttimeRegular = kaiyeStarttimeRegular;
    }

    public String getKaiyeEndtimeRegular() {
        return kaiyeEndtimeRegular;
    }

    public void setKaiyeEndtimeRegular(String kaiyeEndtimeRegular) {
        this.kaiyeEndtimeRegular = kaiyeEndtimeRegular;
    }

    public String getDengjiJiguanRegular() {
        return dengjiJiguanRegular;
    }

    public void setDengjiJiguanRegular(String dengjiJiguanRegular) {
        this.dengjiJiguanRegular = dengjiJiguanRegular;
    }

    public String getHezunTimeRegular() {
        return hezunTimeRegular;
    }

    public void setHezunTimeRegular(String hezunTimeRegular) {
        this.hezunTimeRegular = hezunTimeRegular;
    }

    public String getDengjiStateRegular() {
        return dengjiStateRegular;
    }

    public void setDengjiStateRegular(String dengjiStateRegular) {
        this.dengjiStateRegular = dengjiStateRegular;
    }

    public String getScopeRegular() {
        return scopeRegular;
    }

    public void setScopeRegular(String scopeRegular) {
        this.scopeRegular = scopeRegular;
    }
}
