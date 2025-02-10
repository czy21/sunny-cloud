package com.sunny.system.portal.controller;

import com.sunny.system.core.service.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(path = "socket")
public class SocketController {


    @Autowired
    SocketService socketService;

    @GetMapping(path = "send")
    public Map<String, Object> send(@RequestParam("id") String id, @RequestParam("msg") String msg) throws IOException {
        socketService.sendInfo(id, msg);
        return Map.of();
    }
}
