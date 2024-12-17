package com.wflow.controller;

import com.wflow.service.OrgRepositoryService;
import com.wflow.vo.OrgTreeVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oa/org")
@RequiredArgsConstructor
public class OrgController {

    private final OrgRepositoryService orgRepositoryService;

    @GetMapping("/tree")
    public List<OrgTreeVo> getOrgTree(
            @RequestParam(required = false) String deptId,
            @RequestParam String type) {
        return orgRepositoryService.getOrgTree(deptId, type);
    }

    @GetMapping("/tree/user/search")
    public List<OrgTreeVo> searchUsers(@RequestParam String userName) {
        return orgRepositoryService.getUserByName(userName);
    }
} 