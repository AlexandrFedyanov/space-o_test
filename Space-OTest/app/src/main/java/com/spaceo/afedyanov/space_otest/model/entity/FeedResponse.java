package com.spaceo.afedyanov.space_otest.model.entity;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "result")
public class FeedResponse {

    @Element(name = "totalPages")
    public int totalPages;
    @ElementList(name = "quotes", entry = "quote")
    public List<FeedRecord> feedRecords;
}
