drop table jokes if exists;
CREATE TABLE jokes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    body TEXT,
    created_at TIMESTAMP WITH TIME ZONE
);

drop table articles if exists;
CREATE TABLE articles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR (250),
    author VARCHAR (100),
    content TEXT,
    description TEXT,
    url VARCHAR (200),
    published_at TIMESTAMP WITH TIME ZONE
);