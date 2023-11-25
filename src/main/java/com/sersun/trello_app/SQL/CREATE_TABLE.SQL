CREATE TABLE users (
    userId SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE projects (
    projectId SERIAL PRIMARY KEY,
    project_name VARCHAR(100) NOT NULL,
    project_description VARCHAR(200),
    created_by INT REFERENCES users(userId) ON DELETE CASCADE
);

CREATE TABLE tasks (
    taskId SERIAL PRIMARY KEY,
    task_name VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL,
    projectId INT REFERENCES projects(projectId) ON DELETE CASCADE,
    assigned_to INT REFERENCES users(userid)
);


