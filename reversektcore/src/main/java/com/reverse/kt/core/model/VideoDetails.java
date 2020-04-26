package com.reverse.kt.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by vikas on 02-04-2020.
 */
@Entity
@Table(name="VIDEO_DETAILS")
@Getter
@Setter
@NoArgsConstructor
public class VideoDetails extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="VIDEO_DETAIL_SEQ")
    private Integer videoDetailSeq;

    @OneToOne
    @JoinColumn(name = "PROJECT_ITEM_SEQ")
    private ProjectItem projectItem;

    @Column(name = "VIDEO_NAME")
    private String videoName;

    @Column(name = "VIDEO_DETAILS")
    private String videoDetails;

    @Column(name = "VIDEO_DIRECTORY_PATH")
    private String videoDirectoryPath;

    @Column(name = "VIDEO_DIRECTORY_REF_CD")
    private String videoDirectoryRefCd;
}
