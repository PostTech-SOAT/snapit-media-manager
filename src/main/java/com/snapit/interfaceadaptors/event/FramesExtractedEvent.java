package com.snapit.interfaceadaptors.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class FramesExtractedEvent implements Serializable {

    private String id;

    private String filename;

}
