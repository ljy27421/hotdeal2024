package com.hotdealwork.hotdealwork.ppomppu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PpomppuController {

    @Autowired
    private PpomppuService ppomppuService;

    @GetMapping("/ppomppu")
    public String getPpomppuFeed(Model model) {
        model.addAttribute("ppomppuItems", ppomppuService.getPpomppuFeed());

        return "ppomppuBoard";
    }
}
