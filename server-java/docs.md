# Development Documentation

## Environment Setup

### MySQL Configuration
- Host: localhost
- Port: 3306
- Database: wflow
- Username: root
- Password: root

### Port Configuration
- Backend: 1000 (Spring Boot)
- Frontend: 1024 (Vue.js)

## Database Schema

### Tables
1. Forms (`wflow_forms`)
   - `id` varchar(64) - Primary key
   - `group_id` varchar(64) - Foreign key to `wflow_model_groups.id`
   - `name` varchar(64) - Form name
   - `icon` varchar(255) - Form icon path
   - `form_items` text - Form field definitions (JSON)
   - `process` text - Process definition (JSON)
   - `version` int - Form version
   - `create_time` datetime
   - `update_time` datetime

2. Users (`wflow_users`)
   - `user_id` varchar(64) - Primary key
   - `user_name` varchar(64)
   - `avatar` varchar(255)
   - `create_time` datetime
   - `update_time` datetime

3. Departments (`wflow_departments`)
   - `dept_id` varchar(64) - Primary key
   - `parent_id` varchar(64)
   - `dept_name` varchar(64)
   - `leader` varchar(64)
   - `create_time` datetime
   - `update_time` datetime

4. Model Groups (`wflow_model_groups`)
   - `id` varchar(64) - Primary key
   - `name` varchar(64)
   - `sort` int
   - `create_time` datetime
   - `update_time` datetime

5. User-Department Relationships (`wflow_user_departments`)
   - `id` bigint - Primary key
   - `user_id` varchar(64) - Foreign key to `wflow_users.user_id`
   - `dept_id` varchar(64) - Foreign key to `wflow_departments.dept_id`
   - `create_time` datetime - Default current timestamp
   - `PRIMARY KEY (id)`
   - `KEY idx_user_dept (user_id,dept_id)`

## API Endpoints

### Form Group Management
```
GET /admin/form/group
Response: List<WflowModelGroups>
[
  {
    "id": "1868296083893915649",
    "name": "Test Group",
    "sort": 1,
    "createTime": "2024-12-15T22:04:32",
    "updateTime": "2024-12-15T22:04:32",
    "items": []
  }
]
```

```
POST /admin/form/group
Request Body: {
  "name": "Group Name",
  "sort": 1
}
Response: {
  "message": "创建分组成功",
  "groups": [...]  // Updated list of all groups
}
```

```
PUT /admin/form/group
Request Body: {
  "id": "group_id",
  "name": "New Name",
  "sort": 2
}
Response: 200 OK
```

```
DELETE /admin/form/group/{id}
Response: 200 OK
```

### Form Management API

#### Create Form
```
POST /admin/form
Request Body: {
  "groupId": "group_id",
  "name": "Form Name",
  "icon": "form-icon",
  "formItems": "[...]",
  "process": "[...]",
  "version": 1
}
Response: {
  "message": "创建表单成功"
}
```

#### Update Form
```
PUT /admin/form/detail
Request Body: {
  "id": "form_id",
  "groupId": "group_id",
  "name": "Updated Form Name",
  "icon": "updated-icon",
  "formItems": "[...]",
  "process": "[...]",
  "version": 2
}
Response: {
  "message": "更新表单成功"
}
```

#### Get Form Details
```
GET /admin/form/detail/{id}
Response: {
  "id": "form_id",
  "groupId": "group_id",
  "name": "Form Name",
  "icon": "form-icon",
  "formItems": "[...]",
  "process": "[...]",
  "version": 1,
  "createTime": "2024-12-15T22:04:32",
  "updateTime": "2024-12-15T22:04:32"
}
```

### Organization Management
```
GET /oa/org/tree
Parameters:
- deptId (optional): Department ID
- type: "user" or "dept"
Response: List of organization tree nodes
```

```
GET /oa/org/tree/user/search
Parameters:
- userName: Search keyword
Response: List of matching users
```

## Organization Structure

### Database Schema

#### User-Department Relationships
```sql
CREATE TABLE wflow_user_departments (
    id bigint NOT NULL AUTO_INCREMENT,
    user_id varchar(64) NOT NULL,
    dept_id varchar(64) NOT NULL,
    create_time datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_user_dept (user_id,dept_id)
);
```

### Organization Tree API

#### Get Organization Tree
```
GET /oa/org/tree
Parameters:
- deptId (optional): Department ID to get children
- type: 
  - "user": Return both departments and users
  - "dept": Return only departments

Response Example:
[
  {
    "id": "D001",
    "name": "总公司",
    "type": "dept"
  },
  {
    "id": "U001",
    "name": "张总",
    "avatar": "https://...",
    "type": "user"
  }
]
```

#### Search Users
```
GET /oa/org/tree/user/search
Parameters:
- userName: Search keyword for user names

Response Example:
[
  {
    "id": "U001",
    "name": "张总",
    "avatar": "https://...",
    "type": "user"
  }
]
```

### Organization Structure Rules

1. Department Hierarchy
   - Root level department (总公司)
   - Second level departments (研发部, 市场部, 财务部)
   - Team level departments (前端组, 后端组)

2. User Assignment
   - Each user belongs to exactly one department
   - Department leaders are specified in the departments table
   - Users are associated with departments via wflow_user_departments table

3. Data Relationships
   - Departments use parent_id to create hierarchy
   - Each department has a leader (referenced by user_id)
   - User-Department relationships are maintained in a separate table

### Example Structure
```
总公司 (张总)
├── 研发部 (李研发)
│   ├── 前端组 (王前端)
│   └── 后端组 (赵后端)
├── 市场部 (陈市场, 刘销售, 孙销售)
└── 财务部 (钱财务, 周财务)
```

## Error Handling

### API Response Format
Success Response:
```json
{
  "message": "操作成功消息",
  "groups": [...]  // Optional data
}
```

Error Response:
```json
{
  "message": "错误描述信息"
}
```

### Common Error Messages
- 获取分组异常: Error fetching groups
- 创建分组失败: Error creating group
- 更新分组失败: Error updating group
- 删除分组失败: Error deleting group
- 创建表单失败: Error creating form
- 更新表单失败: Error updating form
- 获取表单失败: Error fetching form

## Development Guidelines

### Database Naming Conventions
- Table names: prefix with `wflow_`
- Primary keys: suffix with `_id`
- Timestamps: use `create_time` and `update_time`
- Foreign keys: suffix with referenced table's primary key name

### Logging
Backend logs include:
- Operation start: "Fetching/Creating/Updating/Deleting..."
- Success messages with relevant data
- Error messages with full stack traces
- Request/Response details for debugging

### Error Handling Best Practices
1. Use try-catch blocks for all database operations
2. Log both success and error cases
3. Return standardized error responses
4. Include meaningful error messages in Chinese
5. Maintain consistent response structure

## Recent Updates and Bug Fixes

### Form Management Implementation
We have successfully implemented the form management system with the following features:

1. Backend Implementation:
   - Created `WflowForms` entity with proper Lombok annotations
   - Implemented `FormController` with CRUD operations
   - Added `FormService` with MyBatis-Plus integration
   - Set up proper error handling and logging

2. Database Integration:
   - Forms are stored in `wflow_models` table (not `wflow_forms`)
   - Form groups are managed in `wflow_model_groups` table
   - Added logo field to support form icons with background colors

3. API Endpoints:
   - Form creation with group assignment
   - Form updates with version control
   - Form retrieval by ID
   - Form group management

### Troubleshooting Guide

#### Database Issues
1. Table Mapping:
   - Forms are actually stored in `wflow_models` table, not `wflow_forms`
   - Entity needs `@TableName("wflow_models")` annotation
   - Required fields: id, group_id, name, logo, form_items, process, version

2. Logo Field:
   - Default format: `{"icon":"el-icon-document","background":"#409EFF"}`
   - Added to both database and entity model
   - Auto-populated if empty

#### API Issues
1. URL Conflicts:
   - Avoid duplicate endpoint mappings between controllers
   - Use different base paths for mock vs real controllers
   - Current mock endpoints: `/admin/form/group` and `/admin/form/group/list`
   - Original endpoints moved to: `/admin/form/group/original`

2. Response Format:
   - Groups must include `items` array even if empty
   - Form responses should include logo information
   - Timestamps should be in format: "2024-12-15 23:35:38"

#### Testing Strategy
1. Mock Data Phase:
   - Using mock data to verify frontend integration
   - Mock data includes realistic group and form structures
   - Helps identify missing fields and format issues

2. Real Data Phase:
   - Gradually replace mock data with database queries
   - Maintain same response structure
   - Verify all CRUD operations

### Next Steps
1. Replace mock data with real database queries
2. Re-enable original controller endpoints
3. Verify form creation and updates
4. Test group management operations
5. Implement proper error handling for database operations

### Form Groups Feature
The form groups feature is now working correctly with:

1. Backend Changes:
   - Modified `ModelGroupController` to include empty `items` array
   - Added detailed logging for group operations
   - Enhanced error handling with specific messages

2. Frontend Integration:
   - Proper group creation and management
   - Real-time updates of group lists
   - Error handling and user feedback

3. Testing:
   - Verified all CRUD operations
   - Confirmed proper error handling
   - Tested group-form relationships

### Key Learnings
1. Always include all required fields in API responses, even if they're empty
2. Implement proper error handling on both frontend and backend
3. Use detailed logging to track operations and troubleshoot issues
4. Maintain consistent data structure between frontend and backend

### Process Initiator Tracking Issue (2024-12-16)

#### Problem Description
Forms submitted by "刘销售" were not appearing in the "我发起的" list despite successful submission.

#### Investigation Steps
1. Frontend Logs Analysis
   - Confirmed successful API calls to `/admin/process/my`
   - Response showed empty processes array `{"processes":[]}`

2. Database Investigation
   ```sql
   -- Check process variables
   SELECT * FROM ACT_RU_VARIABLE WHERE NAME_ IN ('initiator', 'startUser');
   
   -- Check process instances
   SELECT PROC_INST_ID_, START_USER_ID_, START_TIME_ 
   FROM ACT_HI_PROCINST 
   WHERE PROC_INST_ID_ IN (
     SELECT PROC_INST_ID_ 
     FROM ACT_RU_VARIABLE 
     WHERE TEXT_ = '%E5%88%98%E9%94%80%E5%94%AE'
   );
   ```

3. Findings
   - Process variables correctly stored initiator as URL-encoded value
   - `START_USER_ID_` in `ACT_HI_PROCINST` was NULL
   - Process instances were created but not properly tracking initiator

#### Solution Implementation
1. Process Instance Creation
   ```java
   // Changed from direct start to builder pattern
   ProcessInstance processInstance = runtimeService.createProcessInstanceBuilder()
       .processDefinitionId(processDefinitionId)
       .businessKey(form.getId())
       .variables(variables)
       .start();
   ```

2. BPMN Model Configuration
   ```java
   // Added initiator configuration at process level
   ExtensionElement initiatorElement = new ExtensionElement();
   initiatorElement.setName("initiator");
   initiatorElement.setNamespace("http://flowable.org/bpmn");
   initiatorElement.setNamespacePrefix("flowable");
   initiatorElement.setElementText("${initiator}");
   process.addExtensionElement(initiatorElement);
   
   // Added initiator configuration at start event level
   startEvent.setInitiator("initiator");
   ```

3. Identity Service Integration
   ```java
   // Set authenticated user before process start
   identityService.setAuthenticatedUserId(initiator);
   try {
       // Start process instance
       // ...
   } finally {
       identityService.setAuthenticatedUserId(null);
   }
   ```

#### Testing Commands
```bash
# Submit form via curl
curl -X POST 'http://localhost:1000/admin/process/start/1868296083893915649' \
  -H 'Content-Type: application/json' \
  -H 'X-User-Id: %E5%88%98%E9%94%80%E5%94%AE' \
  -d '{"field1": "test value", "field2": "another value"}'

# Check process variables
mysql -uroot -proot wflow -e "SELECT * FROM ACT_RU_VARIABLE WHERE NAME_ IN ('initiator', 'startUser');"

# Check process instances
mysql -uroot -proot wflow -e "SELECT PROC_INST_ID_, START_USER_ID_, START_TIME_ FROM ACT_HI_PROCINST;"
```

#### Key Learnings
1. Flowable process initiator must be set both in:
   - Process variables (`initiator`, `startUser`)
   - Identity service (`setAuthenticatedUserId`)
   - BPMN model configuration (process and start event level)

2. URL encoding considerations:
   - Frontend sends encoded user ID: `%E5%88%98%E9%94%80%E5%94%AE`
   - Backend must decode for display: `刘销售`
   - Database stores encoded version

3. Process instance creation:
   - Builder pattern provides more control
   - Business key helps with process tracking
   - Variables must be set before process start

### URL Encoding Issue with Process Initiator

#### Problem Description
When implementing the "我发起的" (My Initiated Processes) feature, processes were being created but not appearing in the user's initiated process list. Investigation revealed a mismatch in how the initiator values were being stored and queried:

1. Process Creation:
   - Initiator values were being stored URL-encoded in process variables (e.g., "%E5%88%98%E9%94%80%E5%94%AE" for "刘销售")
   - This happened automatically due to the X-User-Id header being URL-encoded

2. Process Querying:
   - The query was using decoded values (e.g., "刘销售")
   - This resulted in no matches being found in the database

#### Troubleshooting Steps
1. Added detailed logging to track variable values:
   ```java
   List<String> allVariables = historyService.createHistoricVariableInstanceQuery()
       .variableName("initiator")
       .list()
       .stream()
       .map(var -> "ProcessId: " + var.getProcessInstanceId() + ", Value: " + var.getValue())
       .collect(Collectors.toList());
   log.info("All initiator variables in history: {}", allVariables);
   ```

2. Discovered URL encoding mismatch in logs:
   ```
   All initiator variables in history: [ProcessId: xxx, Value: %E5%88%98%E9%94%80%E5%94%AE]
   Found process IDs with initiator=刘销售: []
   ```

#### Solution Implementation
1. Process Controller Update:
   ```java
   @GetMapping("/my")
   public ResponseEntity<?> getInitiatedProcesses() {
       String userId = UserUtil.getLoginUserId();
       // URL encode the userId if it's not already encoded
       if (!userId.contains("%")) {
           userId = URLEncoder.encode(userId, "UTF-8");
       }
       Map<String, Object> processes = processService.getInitiatedProcesses(userId);
       return ResponseEntity.ok(processes);
   }
   ```

2. Process Service Update:
   ```java
   public Map<String, Object> getInitiatedProcesses(String userId) {
       // Use encoded userId directly for variable query
       List<String> processInstanceIds = historyService.createHistoricVariableInstanceQuery()
           .variableName("initiator")
           .variableValueEquals("initiator", userId)
           .list();
           
       // Decode for startedBy query
       String decodedUserId = URLDecoder.decode(userId, "UTF-8");
       List<String> processInstanceIds2 = historyService.createHistoricProcessInstanceQuery()
           .startedBy(decodedUserId)
           .list();
   }
   ```

#### Future Improvements
For better maintainability and consistency, consider one of these approaches:

1. **Store Decoded Values**: Modify the process creation to decode values before storage
   ```java
   variables.put("initiator", URLDecoder.decode(initiator, "UTF-8"));
   ```

2. **Consistent Encoding**: Always use encoded values throughout the system
   ```java
   // Ensure values are encoded before storage
   variables.put("initiator", URLEncoder.encode(initiator, "UTF-8"));
   // Use encoded values for queries
   .variableValueEquals("initiator", encodedUserId)
   ```

3. **Normalized Fields**: Add additional fields for normalized (decoded) values
   ```java
   variables.put("initiator", encodedValue);
   variables.put("initiatorNormalized", decodedValue);
   ```

#### Key Learnings
1. URL encoding consistency is crucial when dealing with non-ASCII characters
2. Different parts of the system (HTTP headers, database storage, queries) may handle encoding differently
3. Logging raw values helps identify encoding mismatches
4. Consider encoding strategy early in the development process

## Workflow Implementation Details

### Recent Troubleshooting (2024-12-16)

#### Issue 1: Form Submission Process
1. Problem: Form submissions were failing with compilation errors
2. Root Cause: 
   - Process type ambiguity in WFlowToBpmnCreator
   - SequenceFlow constructor issues
3. Solution:
   - Fixed imports to use correct Process type from Flowable
   - Properly configured SequenceFlow objects with source and target refs

#### Issue 2: Task Assignment and Process Tracking
1. Problem: 
   - Tasks not appearing in approver's "待我处理" list
   - Processes not showing in initiator's "我发起的" list
2. Root Cause:
   - Missing task assignee configuration
   - No process initiator tracking
3. Solution:
   - Added explicit assignee to UserTask: `userTask.setAssignee("张总")`
   - Added process initiator extension element:
     ```java
     ExtensionElement initiatorElement = new ExtensionElement();
     initiatorElement.setName("initiator");
     initiatorElement.setNamespace("http://flowable.org/bpmn");
     initiatorElement.setNamespacePrefix("flowable");
     initiatorElement.setElementText("${initiator}");
     process.addExtensionElement(initiatorElement);
     ```

### Current BPMN Implementation

#### Process Structure
1. Start Event (id: "start")
2. User Task
   - ID: "task_1"
   - Name: "Review Task"
   - Assignee: "张总"
3. End Event (id: "end")
4. Sequence Flows
   - flow1: start → task_1
   - flow2: task_1 → end

#### Technical Details
1. Process Definition
   - Dynamic ID generation: "process_" + processNode.id
   - Name from process definition JSON
   - Initiator tracking enabled

2. Database Tables
   - ACT_RU_EXECUTION: Runtime process execution
   - ACT_RU_TASK: Active user tasks
   - ACT_HI_PROCINST: Process instance history

3. Key Configurations
   ```java
   // Process Configuration
   process.setId("process_" + processNode.path("id").asText());
   process.setName(processNode.path("name").asText());
   
   // Task Configuration
   userTask.setId("task_1");
   userTask.setName("Review Task");
   userTask.setAssignee("张总");
   ```

### Workflow States
1. Process Initiation
   - Form submitted by user
   - Process instance created with initiator tracking
   - Initial task assigned to approver

2. Task Assignment
   - Tasks automatically assigned to "张总"
   - Visible in "待我处理" list
   - Process visible in initiator's "我发起的" list

### Database Schema Details
1. Process Instances (ACT_HI_PROCINST)
   - PROC_INST_ID_: Unique process instance ID
   - BUSINESS_KEY_: Form ID reference
   - START_USER_ID_: Process initiator
   - START_TIME_: Process start timestamp

2. Active Tasks (ACT_RU_TASK)
   - ID_: Task instance ID
   - PROC_INST_ID_: Reference to process instance
   - ASSIGNEE_: Task assignee
   - NAME_: Task display name

### Process Initiator Implementation

#### Overview
The process initiator tracking system handles both URL-encoded Chinese characters and ensures proper storage/retrieval in Flowable's process engine.

#### Key Components
1. Process Variables:
   ```java
   // Store encoded initiator in variables
   variables.put("initiator", initiator);        // e.g. "%E5%88%98%E9%94%80%E5%94%AE"
   variables.put("startUser", initiator);        // Duplicate for compatibility
   ```

2. Identity Service:
   ```java
   // Set decoded initiator for Flowable's internal tracking
   String decodedInitiator = URLDecoder.decode(initiator, "UTF-8");
   identityService.setAuthenticatedUserId(decodedInitiator);
   ```

3. Process Instance Creation:
   ```java
   ProcessInstance processInstance = runtimeService.createProcessInstanceBuilder()
       .processDefinitionId(processDefinitionId)
       .businessKey(form.getId())
       .variables(variables)
       .start();
   ```

#### Testing Process Submission
1. Via Curl:
   ```bash
   curl -X POST 'http://localhost:1000/admin/process/start/d9b122c13b424274a4c1d154004e05c6' \
     -H 'Content-Type: application/json' \
     -H 'X-User-Id: %E5%88%98%E9%94%80%E5%94%AE' \
     -d '{"field1": "test value", "field2": "another value"}'
   ```

2. Expected Response:
   ```json
   {
     "processInstanceId": "34d85315-bbbd-11ef-be61-f6ad770f71e1",
     "message": "流程启动成功"
   }
   ```

#### Troubleshooting Guide

1. Process Not Appearing in "我发起的" List
   - **Symptom**: Process created successfully but not visible in initiator's list
   - **Common Causes**:
     * URL encoding mismatch between storage and query
     * Missing or incorrect initiator variable
   - **Solution**:
     * Ensure consistent URL encoding handling
     * Verify both process variables and identity service are set
     * Check logs for initiator value mismatches

2. Compilation Issues
   - **Symptom**: `startUserId` method not found
   - **Solution**: Use standard Flowable builder pattern:
     ```java
     // Correct way
     .processDefinitionId(processDefinitionId)
     .businessKey(form.getId())
     .variables(variables)
     .start();
     ```

3. Chinese Character Encoding
   - **Symptom**: Garbled characters or encoding issues
   - **Solution**:
     * Use UTF-8 for URL encoding/decoding
     * Keep encoded values in variables
     * Decode only when needed for display or queries

#### Verification Steps
1. Check Process Variables:
   ```sql
   SELECT * FROM ACT_RU_VARIABLE 
   WHERE NAME_ IN ('initiator', 'startUser');
   ```

2. Verify Process Instance:
   ```sql
   SELECT PROC_INST_ID_, START_USER_ID_, START_TIME_ 
   FROM ACT_HI_PROCINST;
   ```

3. Check Historic Variables:
   ```sql
   SELECT * FROM ACT_HI_VARINST 
   WHERE NAME_ = 'initiator';
   ```

#### Best Practices
1. Always maintain URL-encoded values in process variables
2. Use decoded values only for:
   - Setting authenticated user
   - Display purposes
   - Direct database queries
3. Include both `initiator` and `startUser` variables for compatibility
4. Log variable values at key points for debugging
5. Use proper error handling and validation
