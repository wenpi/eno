package com.energicube.eno.pattern.model;
// Generated 2013-8-21 16:23:12 by Hibernate Tools 3.4.0.CR1


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * UcDeviceSystem generated by hbm2java
 */
@Entity
@Table(name = "UC_deviceSystem"
        , schema = "dbo"
)
public class UcDeviceSystem implements java.io.Serializable {


    private String id;
    private String systemName;
    private String specclass;
    private String systemUrl;
    private String systemClass;
    private String isRunPattern;
    private String parentId;
    private String siteId;
    private String orgId;
    private String systemCode;
    private Set<UcDeviceGroup> ucDeviceGroups = new HashSet<UcDeviceGroup>(0);
    private Set<UcSystemParam> ucSystemParams = new HashSet<UcSystemParam>(0);

    public UcDeviceSystem() {
    }


    public UcDeviceSystem(String id) {
        this.id = id;
    }

    public UcDeviceSystem(String id, String systemName, String isRunPattern, String parentId, String siteId, String orgId, Set<UcDeviceGroup> ucDeviceGroups, Set<UcSystemParam> ucSystemParams) {
        this.id = id;
        this.systemName = systemName;
        this.isRunPattern = isRunPattern;
        this.parentId = parentId;
        this.siteId = siteId;
        this.orgId = orgId;
        this.ucDeviceGroups = ucDeviceGroups;
        this.ucSystemParams = ucSystemParams;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Column(name = "systemName", length = 100)
    public String getSystemName() {
        return this.systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @Column(name = "specclass", length = 100)
    public String getSpecclass() {
        return specclass;
    }

    public void setSpecclass(String specclass) {
        this.specclass = specclass;
    }

    @Column(name = "systemUrl", length = 100)
    public String getSystemUrl() {
        return systemUrl;
    }

    public void setSystemUrl(String systemUrl) {
        this.systemUrl = systemUrl;
    }

    @Column(name = "isRunPattern", length = 4)
    public String getIsRunPattern() {
        return this.isRunPattern;
    }

    public void setIsRunPattern(String isRunPattern) {
        this.isRunPattern = isRunPattern;
    }


    @Column(name = "parentId", length = 36)
    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


    @Column(name = "siteId", length = 36)
    public String getSiteId() {
        return this.siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }


    @Column(name = "orgId", length = 36)
    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ucDeviceSystem", cascade = CascadeType.REMOVE)
    public Set<UcDeviceGroup> getUcDeviceGroups() {
        return this.ucDeviceGroups;
    }

    public void setUcDeviceGroups(Set<UcDeviceGroup> ucDeviceGroups) {
        this.ucDeviceGroups = ucDeviceGroups;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ucDeviceSystem", cascade = CascadeType.REMOVE)
    public Set<UcSystemParam> getUcSystemParams() {
        return this.ucSystemParams;
    }

    public void setUcSystemParams(Set<UcSystemParam> ucSystemParams) {
        this.ucSystemParams = ucSystemParams;
    }


    @Column(name = "systemCode", length = 100)
    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    @Column(name = "systemClass", length = 50)
    public String getSystemClass() {
        return systemClass;
    }

    public void setSystemClass(String systemClass) {
        this.systemClass = systemClass;
    }
}

