package com.leomaster.emqxsessionboot.service;

import com.leomaster.emqxsessionboot.param.MessageParam;

public interface EMQXService {

    void pushMessage(MessageParam messageParam);
}
