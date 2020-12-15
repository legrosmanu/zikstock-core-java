package com.zikstock.core.zikresource;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection="zikresources")
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

    // TODO : after authentication implementation, add the field addedBy

    @Size(max=10) @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private List<Tag> tags = new ArrayList<>();

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    public void addTag(String label, String value) {
        tags.add(new Tag(label, value));
    }

    @Data
    @RequiredArgsConstructor
    private class Tag {
        @NonNull
        private String label;
        @NonNull @Size(max=255)
        private String value;

    }

}
