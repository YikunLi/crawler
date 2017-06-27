package crawler.controller;

import crawler.domain.Ad;
import crawler.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by liyikun on 2017/6/25.
 */
@Controller
public class CrawlerController {

    @Autowired
    private CrawlerService crawlerService;

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @ModelAttribute("results")
    public List<Ad> findAll() {
        return this.crawlerService.findAll();
    }

    @RequestMapping("/crawl")
    @ResponseBody
    public String startCrawl() {
        System.out.println("startCrawl");
        return "Success!";
    }
}