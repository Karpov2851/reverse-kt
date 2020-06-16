package com.reverse.kt.core.ui;

import lombok.*;

/**
 * Created by vikas on 11-06-2020.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryView extends BaseModelView{
    private String videoName;
    private String videoDetails;
    private Integer scheduledSessionSeq;

    @Builder
    public HistoryView(String videoName,String videoDetails,Integer scheduledSessionSeq,boolean showError,boolean showSuccess,String showSection,String message){
        super(showError,showSuccess,showSection,message);
        this.videoName = videoName;
        this.videoDetails = videoDetails;
        this.scheduledSessionSeq = scheduledSessionSeq;
    }

}
