CREATE OR REPLACE PROCEDURE sp_assignGrade(
    IN student_id INT,
    IN course_id INT,
    IN arg_grade VARCHAR(5)
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE enrollments
    SET grade = arg_grade
    WHERE s_id = student_id
      AND c_id = course_id;
END;
$$;