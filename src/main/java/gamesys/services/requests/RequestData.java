package gamesys.services.requests;

import org.springframework.stereotype.Service;

@Service
public interface RequestData<T> {
    T sendRequest();
    public void saveResponse(T publisher);
}
