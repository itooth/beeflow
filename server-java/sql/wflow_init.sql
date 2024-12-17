-- wflow custom tables
CREATE DATABASE IF NOT EXISTS wflow DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;

USE wflow;

-- User table
CREATE TABLE IF NOT EXISTS wflow_users (
    user_id varchar(64) NOT NULL COMMENT 'User ID',
    user_name varchar(64) NOT NULL COMMENT 'Username',
    avatar varchar(255) DEFAULT NULL COMMENT 'Avatar URL',
    create_time datetime DEFAULT CURRENT_TIMESTAMP,
    update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Users';

-- Department table
CREATE TABLE IF NOT EXISTS wflow_departments (
    dept_id varchar(64) NOT NULL COMMENT 'Department ID',
    parent_id varchar(64) DEFAULT NULL COMMENT 'Parent department ID',
    dept_name varchar(64) NOT NULL COMMENT 'Department name',
    leader varchar(64) DEFAULT NULL COMMENT 'Department leader user ID',
    create_time datetime DEFAULT CURRENT_TIMESTAMP,
    update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Departments';

-- Role table
CREATE TABLE IF NOT EXISTS wflow_roles (
    role_id varchar(64) NOT NULL COMMENT 'Role ID',
    role_name varchar(64) NOT NULL COMMENT 'Role name',
    create_time datetime DEFAULT CURRENT_TIMESTAMP,
    update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Roles';

-- User-Department relation table
CREATE TABLE IF NOT EXISTS wflow_user_departments (
    id bigint NOT NULL AUTO_INCREMENT,
    user_id varchar(64) NOT NULL COMMENT 'User ID',
    dept_id varchar(64) NOT NULL COMMENT 'Department ID',
    create_time datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_user_dept (user_id,dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='User-Department relations';

-- User-Role relation table
CREATE TABLE IF NOT EXISTS wflow_user_roles (
    id bigint NOT NULL AUTO_INCREMENT,
    user_id varchar(64) NOT NULL COMMENT 'User ID',
    role_id varchar(64) NOT NULL COMMENT 'Role ID',
    create_time datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_user_role (user_id,role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='User-Role relations';

-- Form process model group table
CREATE TABLE IF NOT EXISTS wflow_model_groups (
    id varchar(64) NOT NULL COMMENT 'Group ID',
    name varchar(64) NOT NULL COMMENT 'Group name',
    sort int DEFAULT '0' COMMENT 'Sort order',
    create_time datetime DEFAULT CURRENT_TIMESTAMP,
    update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Form process model groups';

-- Form process model table
CREATE TABLE IF NOT EXISTS wflow_models (
    id varchar(64) NOT NULL COMMENT 'Model ID',
    group_id varchar(64) DEFAULT NULL COMMENT 'Group ID',
    name varchar(64) NOT NULL COMMENT 'Model name',
    icon text COMMENT 'Model icon',
    form_items text COMMENT 'Form design JSON',
    process text COMMENT 'Process design JSON',
    version int DEFAULT '1' COMMENT 'Version number',
    create_time datetime DEFAULT CURRENT_TIMESTAMP,
    update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Form process models';

-- Form process model history table
CREATE TABLE IF NOT EXISTS wflow_model_historys (
    id varchar(64) NOT NULL COMMENT 'History ID',
    model_id varchar(64) NOT NULL COMMENT 'Model ID',
    group_id varchar(64) DEFAULT NULL COMMENT 'Group ID',
    name varchar(64) NOT NULL COMMENT 'Model name',
    icon text COMMENT 'Model icon',
    form_items text COMMENT 'Form design JSON',
    process text COMMENT 'Process design JSON',
    version int DEFAULT '1' COMMENT 'Version number',
    create_time datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_model_version (model_id,version)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Form process model history'; 