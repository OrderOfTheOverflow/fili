// Copyright 2021 Yahoo Inc.
// Licensed under the terms of the Apache license. Please see LICENSE.md file distributed with this work for terms.
package com.yahoo.bard.webservice.sql.helper

import static com.yahoo.bard.webservice.data.time.DefaultTimeGrain.WEEK
import static org.joda.time.DateTimeZone.UTC

import com.yahoo.bard.webservice.data.time.TimeGrain
import com.yahoo.bard.webservice.data.time.ZonedTimeGrain
import com.yahoo.bard.webservice.druid.model.query.DruidAggregationQuery

import org.joda.time.DateTime

import spock.lang.Specification

class SqlTimeConverterSpec extends Specification {

    def "GetIntervalStart with week of year and year on weekyears starting the prior year"() {
        setup:
        DruidAggregationQuery druidQuery = Mock(DruidAggregationQuery)
        druidQuery.getTimeZone() >> UTC
        druidQuery.getGranularity() >> WEEK

        SqlTimeConverter converter = new SqlTimeConverter()
        String[] fields = Arrays.asList(year.toString(), week.toString()).toArray()


        expect:
        converter.getIntervalStart(0, fields, druidQuery).equals(time)

        where:
        time                            | year | week
        new DateTime("2021-12-06", UTC) | 2021 | 49
    }

    def "GetIntervalStart with week of year and year on weekyears starting the prior year (zoned query)"() {
        setup:
        DruidAggregationQuery druidQuery = Mock(DruidAggregationQuery)
        TimeGrain utcWeek = new ZonedTimeGrain(WEEK, UTC)
        druidQuery.getTimeZone() >> UTC
        druidQuery.getGranularity() >> utcWeek

        SqlTimeConverter converter = new SqlTimeConverter()
        String[] fields = Arrays.asList(year.toString(), week.toString()).toArray()


        expect:
        converter.getIntervalStart(0, fields, druidQuery).equals(time)

        where:
        time                            | year | week
        new DateTime("2021-12-06", UTC) | 2021 | 49
    }
}
