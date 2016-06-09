package com.spaceo.afedyanov.space_otest.model.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "quote")
public class FeedRecord {
    @Element(name = "id")
    public int id;
    @Element(name = "date")
    public String date;
    @Element(name = "text")
    public String text;
}