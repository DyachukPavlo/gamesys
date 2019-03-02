package gamesys.domain.jokes;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "jokes")
public class Joke implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "BIGINT")
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String body;

	@Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private LocalDateTime createdAt;

	public Long getId() {
			return id;
		}

	public void setId(Long id) {
			this.id = id;
		}

	public String getBody() {
			return body;
		}

	public void setBody(String body) {
		this.body = body;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Joke(String body) {
		this.body = body;
	}

	public Joke() {
	}
}