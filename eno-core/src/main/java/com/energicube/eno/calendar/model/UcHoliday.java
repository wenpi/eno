package com.energicube.eno.calendar.model;
// Generated 2013-8-21 16:23:12 by Hibernate Tools 3.4.0.CR1


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * UcHoliday generated by hbm2java
 */
@Entity
@Table(name = "UC_holiday"
        
)
public class UcHoliday implements java.io.Serializable {


    private String id;
    @org.codehaus.jackson.annotate.JsonBackReference
    @com.fasterxml.jackson.annotation.JsonBackReference
    private UcCalendar ucCalendar;
    private String isHoliday;
    private String holidayName;
    private String holidayType;

    public UcHoliday() {
    }


    public UcHoliday(String id) {
        this.id = id;
    }

    public UcHoliday(String id, UcCalendar ucCalendar, String isHoliday, String holidayName, String holidayType) {
        this.id = id;
        this.ucCalendar = ucCalendar;
        this.isHoliday = isHoliday;
        this.holidayName = holidayName;
        this.holidayType = holidayType;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "calendarId")
    public UcCalendar getUcCalendar() {
        return this.ucCalendar;
    }

    public void setUcCalendar(UcCalendar ucCalendar) {
        this.ucCalendar = ucCalendar;
    }


    @Column(name = "isHoliday", length = 2)
    public String getIsHoliday() {
        return this.isHoliday;
    }

    public void setIsHoliday(String isHoliday) {
        this.isHoliday = isHoliday;
    }


    @Column(name = "holidayName", length = 30)
    public String getHolidayName() {
        return this.holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }


    @Column(name = "holidayType", length = 4)
    public String getHolidayType() {
        return this.holidayType;
    }

    public void setHolidayType(String holidayType) {
        this.holidayType = holidayType;
    }


}


