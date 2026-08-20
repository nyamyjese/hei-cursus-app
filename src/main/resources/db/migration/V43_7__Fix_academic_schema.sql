DROP TABLE IF EXISTS grade_history CASCADE;
DROP TABLE IF EXISTS grade CASCADE;
DROP TABLE IF EXISTS exam CASCADE;
DROP TABLE IF EXISTS course_assignment CASCADE;
DROP TABLE IF EXISTS course CASCADE;
DROP TABLE IF EXISTS group_membership CASCADE;
DROP TABLE IF EXISTS teacher CASCADE;
DROP TABLE IF EXISTS student CASCADE;
DROP TABLE IF EXISTS "group" CASCADE;
DROP TABLE IF EXISTS promotion CASCADE;
DROP TABLE IF EXISTS track CASCADE;
DROP TABLE IF EXISTS "user" CASCADE;

CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       last_name VARCHAR(255) NOT NULL,
                       first_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL
);

CREATE TABLE tracks (
                        id UUID PRIMARY KEY,
                        code VARCHAR(50) NOT NULL UNIQUE,
                        label VARCHAR(255) NOT NULL
);

CREATE TABLE promotions (
                            id UUID PRIMARY KEY,
                            ref VARCHAR(50) NOT NULL UNIQUE,
                            year_start INT NOT NULL,
                            year_end INT NOT NULL
);

CREATE TABLE groups (
                        id UUID PRIMARY KEY,
                        ref VARCHAR(50) NOT NULL,
                        year_level INT NOT NULL,
                        promotion_id UUID NOT NULL REFERENCES promotions(id),
                        track_id UUID NOT NULL REFERENCES tracks(id)
);

CREATE TABLE students (
                          id UUID PRIMARY KEY,
                          user_id UUID NOT NULL UNIQUE REFERENCES users(id),
                          track_id UUID NOT NULL REFERENCES tracks(id),
                          std VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE teachers (
                          id UUID PRIMARY KEY,
                          user_id UUID NOT NULL UNIQUE REFERENCES users(id)
);

CREATE TABLE group_membership (
                                  id UUID PRIMARY KEY,
                                  student_id UUID NOT NULL REFERENCES students(id),
                                  group_id UUID NOT NULL REFERENCES groups(id),
                                  start_date DATE NOT NULL,
                                  end_date DATE
);

CREATE TABLE courses (
                         id UUID PRIMARY KEY,
                         ref VARCHAR(50) NOT NULL UNIQUE,
                         title VARCHAR(255) NOT NULL,
                         credits INT NOT NULL,
                         semester INT NOT NULL,
                         track_id UUID NOT NULL REFERENCES tracks(id)
);

CREATE TABLE course_assignment (
                                   id UUID PRIMARY KEY,
                                   course_id UUID NOT NULL REFERENCES courses(id),
                                   teacher_id UUID NOT NULL REFERENCES teachers(id),
                                   group_id UUID NOT NULL REFERENCES groups(id),
                                   UNIQUE (course_id, teacher_id, group_id)
);

CREATE TABLE exams (
                       id UUID PRIMARY KEY,
                       course_id UUID NOT NULL REFERENCES courses(id),
                       date_exams TIMESTAMP NOT NULL,
                       coefficient NUMERIC(3,2) NOT NULL,
                       session VARCHAR(20) NOT NULL DEFAULT 'NORMALE'
);

CREATE TABLE grades (
                        id UUID PRIMARY KEY,
                        exam_id UUID NOT NULL REFERENCES exams(id),
                        student_id UUID NOT NULL REFERENCES students(id),
                        value NUMERIC(4,2) NOT NULL,
                        created_at TIMESTAMP NOT NULL,
                        updated_at TIMESTAMP NOT NULL,
                        UNIQUE (exam_id, student_id)
);

CREATE TABLE grade_histories (
                                 id UUID PRIMARY KEY,
                                 grade_id UUID NOT NULL REFERENCES grades(id),
                                 old_value NUMERIC(4,2) NOT NULL,
                                 new_value NUMERIC(4,2) NOT NULL,
                                 reason VARCHAR(500) NOT NULL,
                                 modified_by_user_id UUID NOT NULL REFERENCES users(id),
                                 modified_at TIMESTAMP NOT NULL
);