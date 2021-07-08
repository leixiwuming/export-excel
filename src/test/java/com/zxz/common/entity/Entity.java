package com.zxz.common.entity;

import com.zxz.common.excel.annotation.AnnotationType;
import com.zxz.common.excel.annotation.Mapping;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@AnnotationType(Mapping.class)
@Validated
public class Entity {
    @Mapping(value = "主键",textColumn = true)
    private Long id;
    @Mapping(value = "名称",notNull = true)
    @NotEmpty(message = "名称不能为空")
    private String name;
    @Mapping("长度")
    private Double length;
    @Mapping("容量")
    private Integer size;
    @Mapping("余额")
    private BigDecimal pay;
    @Mapping("标记")
    private Boolean flag;
    @Mapping(value = "是否删除", dict = {"未删除", "已删除"})
    private Integer deleted;
    @Mapping("创建时间")
    private LocalDateTime createDate;
    @Mapping(value = "身份证",textColumn = true)
    private String idCard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public BigDecimal getPay() {
        return pay;
    }

    public void setPay(BigDecimal pay) {
        this.pay = pay;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
