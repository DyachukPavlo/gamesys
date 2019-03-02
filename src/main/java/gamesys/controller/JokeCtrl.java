package gamesys.controller;

import gamesys.services.JokeSrv;
import gamesys.utils.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jokes")
public class JokeCtrl {
    private final JokeSrv jokeSrv;

    public JokeCtrl(JokeSrv jokeSrv) {
        this.jokeSrv = jokeSrv;
    }

    @GetMapping("/all")
    public Response findAll(){
        return Response.ok(jokeSrv.findAll());
    }

    @GetMapping("/recent")
    public Response findRecent(@RequestParam int quantity){
        return Response.ok(jokeSrv.findFew(quantity));
    }
}
