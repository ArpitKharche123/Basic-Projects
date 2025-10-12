BEGIN
    RETURN CASE UPPER(arg_grade)
        WHEN 'A+' THEN 10.0
        WHEN 'A' THEN 9.0
        WHEN 'B+' THEN 8.0
        WHEN 'B' THEN 7.0
        WHEN 'C+' THEN 6.0
        WHEN 'C' THEN 5.0
        WHEN 'D' THEN 4.0
        WHEN 'F' THEN 0.0
        ELSE 0.0  -- Default for invalid grades
    END;
	END;