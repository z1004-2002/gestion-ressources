package com.yowyob.gestion_ressources.domain.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("images")
public class Image {
    @PrimaryKey
    private String id;
    private String name;
    private String path;
    private Long size;
	private String fileType;
    private String idRessource;
}
