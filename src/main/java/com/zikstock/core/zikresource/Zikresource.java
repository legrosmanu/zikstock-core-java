package com.zikstock.core.zikresource;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection="zikresources")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Zikresource {

    @Id
    private String id;

    @NotBlank(message = "url is mandatory")
    private String url;

    @NotBlank(message = "title is mandatory") @Size(max=255)
    private String title;

    @Size(max=255)
    private String type;

    @Size(max=255)
    private String artist;

    @Size(max=10)
    private List<ZikresourceTag> tags = new ArrayList<>();

}
