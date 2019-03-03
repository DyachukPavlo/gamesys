package gamesys.services.utils;

import gamesys.exceptions.NullValueException;
import gamesys.exceptions.TaskAlreadyExistsException;
import gamesys.exceptions.TaskNotFoundException;
import gamesys.services.requests.GoogleReqSrv;
import gamesys.services.requests.JokeReqSrv;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
public class ScheduleTasksTest {
    @MockBean
    private JokeReqSrv jokeReqSrv;
    @MockBean
    private GoogleReqSrv googleReqSrv;

    private Runnable jokeTask;
    private Runnable googleTask;
    private String jokeTaskName;
    private String googleTaskName;

    @Before
    public void setUp() {
        jokeTask = () -> jokeReqSrv.saveResponse(jokeReqSrv.sendRequest());
        googleTask = () -> googleReqSrv.saveResponse(googleReqSrv.sendRequest());

        jokeTaskName = "JOKE_TASK";
        googleTaskName = "GOOGLE_NEWS_TASK";
    }

    @Test(expected = TaskAlreadyExistsException.class)
    public void taskAlreadyExistsException() throws TaskNotFoundException, NullValueException, TaskAlreadyExistsException {
        try {
            ScheduleTasks.runTask(jokeTaskName, jokeTask, 5, TimeUnit.SECONDS);
            ScheduleTasks.runTask(jokeTaskName, jokeTask, 5, TimeUnit.SECONDS);
        } catch (TaskAlreadyExistsException e) {
            throw new TaskAlreadyExistsException("Task already exists");
        }
        finally {
            ScheduleTasks.stopTask(jokeTaskName);
        }
    }

    @Test(expected = TaskNotFoundException.class)
    public void taskNotFoundException() throws TaskNotFoundException {
        ScheduleTasks.stopTask(jokeTaskName);
    }

    @Test
    public void existingRunTaskInTaskList() throws TaskAlreadyExistsException, TaskNotFoundException, NullValueException {
        ScheduleTasks.runTask(jokeTaskName, jokeTask, 5, TimeUnit.SECONDS);
        assertTrue(ScheduleTasks.showActiveTasks().containsKey(jokeTaskName));
        ScheduleTasks.stopTask(jokeTaskName);
    }

    @Test(expected = NullValueException.class)
    public void nullValueExceptionAtTaskName() throws TaskAlreadyExistsException, NullValueException {
        ScheduleTasks.runTask(null, jokeTask, 5, TimeUnit.SECONDS);
    }

    @Test(expected = NullValueException.class)
    public void nullValueExceptionAtRunnable() throws TaskAlreadyExistsException, NullValueException {
        ScheduleTasks.runTask(jokeTaskName, null, 5, TimeUnit.SECONDS);
    }

    @Test
    public void absenceStoppedTaskInTaskList() throws TaskNotFoundException, TaskAlreadyExistsException, NullValueException {
        ScheduleTasks.runTask(jokeTaskName, jokeTask, 5, TimeUnit.SECONDS);
        ScheduleTasks.stopTask(jokeTaskName);
        assertFalse(ScheduleTasks.showActiveTasks().containsKey(jokeTask));
    }

    @Test
    public void taskQuantity() throws TaskAlreadyExistsException, NullValueException {
        ScheduleTasks.runTask(jokeTaskName, jokeTask, 5, TimeUnit.SECONDS);
        ScheduleTasks.runTask(googleTaskName, googleTask, 15, TimeUnit.MINUTES);
        assertEquals(2, ScheduleTasks.showActiveTasks().size());
    }
}