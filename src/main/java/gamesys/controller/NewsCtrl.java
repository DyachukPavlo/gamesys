package gamesys.controller;

import gamesys.services.ArticleSrv;
import gamesys.utils.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsCtrl {
    private final ArticleSrv articleSrv;

    public NewsCtrl(ArticleSrv articleSrv) {
        this.articleSrv = articleSrv;
    }

    @GetMapping("/all")
    public Response findAll(){
        return Response.ok(articleSrv.findAll());
    }

    @GetMapping("/recent")
    public Response findRecent(@RequestParam int quantity){
        return Response.ok(articleSrv.findFew(quantity));
    }
}
