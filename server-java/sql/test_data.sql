-- Clear existing test data
DELETE FROM wflow_users;
DELETE FROM wflow_departments;
DELETE FROM wflow_user_departments;
DELETE FROM wflow_model_groups;

-- Insert departments
INSERT INTO wflow_departments (dept_id, parent_id, dept_name, leader, create_time, update_time) VALUES
('D001', NULL, '总公司', 'U001', NOW(), NOW()),
('D002', 'D001', '研发部', 'U002', NOW(), NOW()),
('D003', 'D001', '市场部', 'U005', NOW(), NOW()),
('D004', 'D001', '财务部', 'U008', NOW(), NOW()),
('D005', 'D002', '前端组', 'U003', NOW(), NOW()),
('D006', 'D002', '后端组', 'U004', NOW(), NOW());

-- Insert users
INSERT INTO wflow_users (user_id, user_name, avatar, create_time, update_time) VALUES
('U001', '张总', 'https://api.dicebear.com/7.x/avataaars/svg?seed=1', NOW(), NOW()),
('U002', '李研发', 'https://api.dicebear.com/7.x/avataaars/svg?seed=2', NOW(), NOW()),
('U003', '王前端', 'https://api.dicebear.com/7.x/avataaars/svg?seed=3', NOW(), NOW()),
('U004', '赵后端', 'https://api.dicebear.com/7.x/avataaars/svg?seed=4', NOW(), NOW()),
('U005', '陈市场', 'https://api.dicebear.com/7.x/avataaars/svg?seed=5', NOW(), NOW()),
('U006', '刘销售', 'https://api.dicebear.com/7.x/avataaars/svg?seed=6', NOW(), NOW()),
('U007', '孙销售', 'https://api.dicebear.com/7.x/avataaars/svg?seed=7', NOW(), NOW()),
('U008', '钱财务', 'https://api.dicebear.com/7.x/avataaars/svg?seed=8', NOW(), NOW()),
('U009', '周财务', 'https://api.dicebear.com/7.x/avataaars/svg?seed=9', NOW(), NOW()),
('U010', '吴开发', 'https://api.dicebear.com/7.x/avataaars/svg?seed=10', NOW(), NOW()),
('U011', '郑开发', 'https://api.dicebear.com/7.x/avataaars/svg?seed=11', NOW(), NOW()),
('U012', '冯开发', 'https://api.dicebear.com/7.x/avataaars/svg?seed=12', NOW(), NOW());

-- Insert user-department relationships
INSERT INTO wflow_user_departments (user_id, dept_id) VALUES
('U001', 'D001'),  -- 张总 in 总公司
('U002', 'D002'),  -- 李研发 in 研发部
('U003', 'D005'),  -- 王前端 in 前端组
('U004', 'D006'),  -- 赵后端 in 后端组
('U005', 'D003'),  -- 陈市场 in 市场部
('U006', 'D003'),  -- 刘销售 in 市场部
('U007', 'D003'),  -- 孙销售 in 市场部
('U008', 'D004'),  -- 钱财务 in 财务部
('U009', 'D004'),  -- 周财务 in 财务部
('U010', 'D002'),  -- 吴开发 in 研发部
('U011', 'D002'),  -- 郑开发 in 研发部
('U012', 'D002');  -- 冯开发 in 研发部

-- Insert initial model groups
INSERT INTO wflow_model_groups (id, name, sort, create_time, update_time) VALUES
('G001', '人事审批', 1, NOW(), NOW()),
('G002', '财务报销', 2, NOW(), NOW()),
('G003', '行政管理', 3, NOW(), NOW()),
('G004', '项目审批', 4, NOW(), NOW());