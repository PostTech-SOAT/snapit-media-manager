package com.snapit.interfaceadaptors.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class FramesExtractionFailedEvent implements Serializable {

    private String id;

}
