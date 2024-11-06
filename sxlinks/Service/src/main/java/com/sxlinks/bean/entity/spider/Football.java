package com.sxlinks.bean.entity.spider;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "football")
@Data
public class Football {
    @Id
    private Long id;    //编号
    //基本信息
    @Column
    private String name;    //名称
    @Column(name="home_id")
    private Long homeId;    //本方id
    @Column(name="away_id")
    private Long awayId;     //对方id
    @Column(name="home_name")
    private String homeName; //本方名称
    @Column(name="away_name")
    private String awayName;    //对方名称
    @Column(name="start_time")
    private Date startTime;    //开始时间
    @Column(name="finish_time")
    private Date finishTime;      //结束时间-可要可不要
    @Column(name="total_jinqiu")
    private Integer totalJinqiu;    //总进球
    @Column(name="home_jinqiu")
    private Integer homeJinqiu;    //本方进球
    @Column(name="awayJinqiu")
    private Integer awayJinqiu;  //对方进球
    @Column(name="homeShemen1")
    private Integer homeShemen1;  //本方射门射正
    @Column(name="homeShemen2")
    private Integer homeShemen2;    //本方射门2
    @Column(name="awayShemen1")
    private Integer awayShemen1; //对方射门射正
    @Column(name="awayShemen2")
    private Integer awayShemen2; //对方射门2
    @Column(name="home_jg")
    private Integer homeJg; //本方进攻
    @Column(name="away_jg")
    private Integer awayJg;    //对方进攻
    @Column(name="home_wxjg")
    private Integer homeWxjg; //本方危险进攻
    @Column(name="away_wxjg")
    private Integer awayWxjg;    //对方危险进攻
    @Column(name="home_kql")
    private Integer homeKql; //本方控球率
    @Column(name="away_kql")
    private Integer awayKql;    //对方控球率
    @Column(name="away_jiaoqiu")
    private Integer awayJiaoqiu; //对方脚球
    @Column(name="home_jiaoqiu")
    private Integer homeJiaoqiu;    //本方脚球
    @Column(name="home_huangpai")
    private Integer homeHuangpai; //本方黄牌
    @Column(name="away_huangpai")
    private Integer awayHuangpai;    //对方黄牌
    @Column(name="home_hongpai")
    private Integer homeHongpai; //本方红牌
    @Column(name="away_hongpai")
    private Integer awayHongpai;    //对方红牌
    @Column(name="home_dianqiu")
    private Integer homeDianqiu; //本方点球
    @Column(name="away_dianqiu")
    private Integer awayDianqiu;    //对方点球
    @Column(name="state")
    private String state;    //状态(0-未开始 1-正在比赛 2-已结束)

    //附属字段
    @Column(name="create_time")
    private Date createTime;    //创建时间
    @Column(name="create_by")
    private Long createBy;  //创建人
    @Column(name="modify_time")
    private Date modifyTime;    //创建时间
    @Column(name="modify_by")
    private Long modifyBy;  //创建人

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

    public Long getHomeId() {
        return homeId;
    }

    public void setHomeId(Long homeId) {
        this.homeId = homeId;
    }

    public Long getAwayId() {
        return awayId;
    }

    public void setAwayId(Long awayId) {
        this.awayId = awayId;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getAwayName() {
        return awayName;
    }

    public void setAwayName(String awayName) {
        this.awayName = awayName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getTotalJinqiu() {
        return totalJinqiu;
    }

    public void setTotalJinqiu(Integer totalJinqiu) {
        this.totalJinqiu = totalJinqiu;
    }

    public Integer getHomeJinqiu() {
        return homeJinqiu;
    }

    public void setHomeJinqiu(Integer homeJinqiu) {
        this.homeJinqiu = homeJinqiu;
    }

    public Integer getAwayJinqiu() {
        return awayJinqiu;
    }

    public void setAwayJinqiu(Integer awayJinqiu) {
        this.awayJinqiu = awayJinqiu;
    }

    public Integer getHomeShemen1() {
        return homeShemen1;
    }

    public void setHomeShemen1(Integer homeShemen1) {
        this.homeShemen1 = homeShemen1;
    }

    public Integer getHomeShemen2() {
        return homeShemen2;
    }

    public void setHomeShemen2(Integer homeShemen2) {
        this.homeShemen2 = homeShemen2;
    }

    public Integer getAwayShemen1() {
        return awayShemen1;
    }

    public void setAwayShemen1(Integer awayShemen1) {
        this.awayShemen1 = awayShemen1;
    }

    public Integer getAwayShemen2() {
        return awayShemen2;
    }

    public void setAwayShemen2(Integer awayShemen2) {
        this.awayShemen2 = awayShemen2;
    }

    public Integer getHomeJg() {
        return homeJg;
    }

    public void setHomeJg(Integer homeJg) {
        this.homeJg = homeJg;
    }

    public Integer getAwayJg() {
        return awayJg;
    }

    public void setAwayJg(Integer awayJg) {
        this.awayJg = awayJg;
    }

    public Integer getHomeWxjg() {
        return homeWxjg;
    }

    public void setHomeWxjg(Integer homeWxjg) {
        this.homeWxjg = homeWxjg;
    }

    public Integer getAwayWxjg() {
        return awayWxjg;
    }

    public void setAwayWxjg(Integer awayWxjg) {
        this.awayWxjg = awayWxjg;
    }

    public Integer getHomeKql() {
        return homeKql;
    }

    public void setHomeKql(Integer homeKql) {
        this.homeKql = homeKql;
    }

    public Integer getAwayKql() {
        return awayKql;
    }

    public void setAwayKql(Integer awayKql) {
        this.awayKql = awayKql;
    }

    public Integer getAwayJiaoqiu() {
        return awayJiaoqiu;
    }

    public void setAwayJiaoqiu(Integer awayJiaoqiu) {
        this.awayJiaoqiu = awayJiaoqiu;
    }

    public Integer getHomeJiaoqiu() {
        return homeJiaoqiu;
    }

    public void setHomeJiaoqiu(Integer homeJiaoqiu) {
        this.homeJiaoqiu = homeJiaoqiu;
    }

    public Integer getHomeHuangpai() {
        return homeHuangpai;
    }

    public void setHomeHuangpai(Integer homeHuangpai) {
        this.homeHuangpai = homeHuangpai;
    }

    public Integer getAwayHuangpai() {
        return awayHuangpai;
    }

    public void setAwayHuangpai(Integer awayHuangpai) {
        this.awayHuangpai = awayHuangpai;
    }

    public Integer getHomeHongpai() {
        return homeHongpai;
    }

    public void setHomeHongpai(Integer homeHongpai) {
        this.homeHongpai = homeHongpai;
    }

    public Integer getAwayHongpai() {
        return awayHongpai;
    }

    public void setAwayHongpai(Integer awayHongpai) {
        this.awayHongpai = awayHongpai;
    }

    public Integer getHomeDianqiu() {
        return homeDianqiu;
    }

    public void setHomeDianqiu(Integer homeDianqiu) {
        this.homeDianqiu = homeDianqiu;
    }

    public Integer getAwayDianqiu() {
        return awayDianqiu;
    }

    public void setAwayDianqiu(Integer awayDianqiu) {
        this.awayDianqiu = awayDianqiu;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
}
