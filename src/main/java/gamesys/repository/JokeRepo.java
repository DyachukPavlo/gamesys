package gamesys.repository;

import gamesys.domain.jokes.Joke;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JokeRepo implements CustomCrudRepo<Joke>{
	private final EntityManager entityManager;

	public JokeRepo(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List findAll() {
		return entityManager.createNativeQuery("select * from jokes", Joke.class).getResultList();
	}

	public List findLastFew(int quantity) {
		return entityManager.createNativeQuery("select TOP :quantity * from jokes order by id desc", Joke.class)
				.setParameter("quantity", quantity).getResultList();
	}

	@Transactional
	public void save(Joke joke) {
		entityManager.createNativeQuery("insert into jokes (body, created_at) values (:body, :created_at)")
				.setParameter("body", joke.getBody())
				.setParameter("created_at", LocalDateTime.now()).executeUpdate();
		entityManager.close();
	}

}