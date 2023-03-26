package io.github.jihch.controller;

import com.netflix.discovery.converters.Auto;
import io.github.jihch.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping("/client")
    public String client() {
        return clientService.client();
    }

}
