-- CREATE TABLE users (
--     userId SERIAL PRIMARY KEY,
--     username VARCHAR(50) NOT NULL,
--     password VARCHAR(255) NOT NULL,
--     email VARCHAR(100) NOT NULL
-- );
--
-- CREATE TABLE projects (
--     projectId SERIAL PRIMARY KEY,
--     project_name VARCHAR(100) NOT NULL,
--     project_description VARCHAR(200),
--     created_by INT REFERENCES users(userId) ON DELETE CASCADE
-- );
--
-- CREATE TABLE tasks (
--     taskId SERIAL PRIMARY KEY,
--     task_name VARCHAR(255) NOT NULL,
--     description TEXT,
--     status VARCHAR(50) NOT NULL,
--     projectId INT REFERENCES projects(projectId) ON DELETE CASCADE,
--     assigned_to INT REFERENCES users(userid)
-- );
-- New Structure of database
-- Добавление таблицы "users"
CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL
);

-- Создание таблицы "projects"
CREATE TABLE projects (
                          project_id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          start_date DATE,
                          end_date DATE,
                          CONSTRAINT valid_date CHECK (start_date <= end_date)
);

-- Создание таблицы "tasks"
CREATE TABLE tasks (
                       task_id SERIAL PRIMARY KEY,
                       project_id INT REFERENCES projects(project_id) ON DELETE CASCADE,
                       name VARCHAR(255) NOT NULL,
                       description TEXT,
                       due_date DATE,
                       status VARCHAR(20) CHECK (status IN ('In progress', 'Done'))
);

-- Создание таблицы "assignments"
CREATE TABLE assignments (
                             assignment_id SERIAL PRIMARY KEY,
                             task_id INT REFERENCES tasks(task_id) ON DELETE CASCADE,
                             assigned_to INT REFERENCES users(user_id) ON DELETE CASCADE,
                             CONSTRAINT unique_assignment UNIQUE (task_id, assigned_to)
);

-- Создание индексов для ускорения поиска
CREATE INDEX idx_project_name ON projects(name);
CREATE INDEX idx_task_name ON tasks(name);
CREATE INDEX idx_task_status ON tasks(status);
CREATE INDEX idx_task_due_date ON tasks(due_date);
CREATE INDEX idx_user_username ON users(username);

