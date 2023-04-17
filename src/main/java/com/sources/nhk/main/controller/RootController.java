package com.sources.nhk.main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootController {

    @GetMapping("/")
    public String index()
    {


        return "Hello World!!!";
    }


    @GetMapping("/test2")
    public String test()
    {
        for(int i =0; i <12; i++)
        {{
            System.out.println("Xin chao \n");
            if(i == 1)
            {
                System.out.println(1);
                    if(true)
                    {
                        System.out.println("Xin chao duplicate");
                    }
            }
        }}

        return "OK! finished";
    }
}
