CREATE TABLE "user" (
                        id UUID PRIMARY KEY,
                        email VARCHAR(255) UNIQUE NOT NULL,
                        role VARCHAR(50) NOT NULL
);

CREATE TABLE track (
                       id UUID PRIMARY KEY,
                       code VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE student (
                         id UUID PRIMARY KEY,
                         user_id UUID REFERENCES "user"(id),
                         std VARCHAR(50) UNIQUE NOT NULL,
                         track_id UUID REFERENCES track(id)
);

CREATE TABLE teacher (
                         id UUID PRIMARY KEY,
                         user_id UUID REFERENCES "user"(id)
);

CREATE TABLE promotion (
                           id UUID PRIMARY KEY,
                           name VARCHAR(255) NOT NULL
);

CREATE TABLE "group" (
                         id UUID PRIMARY KEY,
                         promotion_id UUID REFERENCES promotion(id),
                         track_id UUID REFERENCES track(id),
                         name VARCHAR(50) NOT NULL
);

CREATE TABLE group_membership (
                                  id UUID PRIMARY KEY,
                                  student_id UUID REFERENCES student(id),
                                  group_id UUID REFERENCES "group"(id),
                                  start_date DATE NOT NULL,
                                  end_date DATE
);

CREATE TABLE course (
                        id UUID PRIMARY KEY,
                        track_id UUID REFERENCES track(id),
                        title VARCHAR(255) NOT NULL,
                        credits INT NOT NULL,
                        semester INT NOT NULL
);

CREATE TABLE course_assignment (
                                   id UUID PRIMARY KEY,
                                   course_id UUID REFERENCES course(id),
                                   teacher_id UUID REFERENCES teacher(id),
                                   group_id UUID REFERENCES "group"(id)
);

CREATE TABLE exam (
                      id UUID PRIMARY KEY,
                      course_id UUID REFERENCES course(id),
                      exam_date DATE NOT NULL,
                      coefficient DECIMAL(3, 2) NOT NULL,
                      session_type VARCHAR(50) NOT NULL
);

CREATE TABLE grade (
                       id UUID PRIMARY KEY,
                       exam_id UUID REFERENCES exam(id),
                       student_id UUID REFERENCES student(id),
                       value DECIMAL(4, 2) NOT NULL
);

CREATE TABLE grade_history (
                               id UUID PRIMARY KEY,
                               grade_id UUID REFERENCES grade(id),
                               old_value DECIMAL(4, 2) NOT NULL,
                               new_value DECIMAL(4, 2) NOT NULL,
                               modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               reason TEXT NOT NULL
);