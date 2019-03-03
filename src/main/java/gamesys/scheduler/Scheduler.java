package gamesys.scheduler;

import gamesys.exceptions.NullValueException;
import gamesys.exceptions.TaskAlreadyExistsException;
import gamesys.services.requests.GoogleReqSrv;
import gamesys.services.requests.JokeReqSrv;
import gamesys.services.utils.ScheduleTasks;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service
public class Scheduler {
    private final JokeReqSrv jokeReqSrv;
    private final GoogleReqSrv googleReqSrv;

    public Scheduler(JokeReqSrv jokeReqSrv, GoogleReqSrv googleReqSrv) {
        this.jokeReqSrv = jokeReqSrv;
        this.googleReqSrv = googleReqSrv;
    }

    @PostConstruct
    public void schedule() throws TaskAlreadyExistsException, NullValueException {
        Runnable jokeTask = () -> jokeReqSrv.saveResponse(jokeReqSrv.sendRequest());
        Runnable googleTask = () -> googleReqSrv.saveResponse(googleReqSrv.sendRequest());
        ScheduleTasks.runTask("JOKES", jokeTask, 5, TimeUnit.SECONDS);
        ScheduleTasks.runTask("GOOGLE_NEWS", googleTask, 15, TimeUnit.MINUTES);
    }
}
