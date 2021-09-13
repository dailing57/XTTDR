package com.xttdr.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@TableName("video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @TableId("video_id")
    private String videoId;
    private String courseId;
    private String path;
    private Date createdTime;
    private String name;
    private Integer seq;
}
