package com.manju.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="wikimedia_recentchange")
@Getter
@Setter
public class WikimediaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Lob
    private String wikiEventData;

    public void setWikiEventData(String wikiEventData) {
        this.wikiEventData = wikiEventData;
    }

    public String getWikiEventData() {
        return wikiEventData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

