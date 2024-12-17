-- Create forms table with proper constraints
CREATE TABLE IF NOT EXISTS wflow_forms (
    id VARCHAR(64) PRIMARY KEY,
    group_id VARCHAR(64),
    name VARCHAR(64),
    icon VARCHAR(255),
    form_items TEXT,
    process TEXT,
    version INT,
    create_time DATETIME,
    update_time DATETIME,
    FOREIGN KEY (group_id) REFERENCES wflow_model_groups(id)
); 