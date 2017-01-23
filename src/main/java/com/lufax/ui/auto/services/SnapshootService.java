package com.lufax.ui.auto.services;

import com.lufax.ui.auto.caseobj.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by Jc on 16/8/15.
 */

@Service
public class SnapshootService {

    @Autowired
    public DriverGeneratorService driverGeneratorService;

    public void getSnapshootAsFile(Step step){

    }


}
