package com.sydml.controller;

import com.sydml.model.entity.Result;
import com.sydml.model.entity.StatusCode;
import com.sydml.model.vo.NewestLogVO;
import com.sydml.service.LogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 日志api
 *
 * @author zzx
 * @date 2021-05-29 13:26
 */
@Api(tags = "日志api")
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    LogService logService;

    @PostMapping("/findNewestLog")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Result findNewestLog(NewestLogVO vo) {
        if (ObjectUtils.isEmpty(vo.getSize())) {
            vo.setSize(10);
        }
        if (1 == vo.getType()) {
            return Result.create(StatusCode.OK, "ok", logService.findControllerNewestLog
                    (vo.getLeft(), vo.getRight(), vo.getSize()));
        } else {
            return Result.create(StatusCode.OK, "ok", logService.findSqlNewestLog
                    (vo.getLeft(), vo.getRight(), vo.getSize()));
        }
    }
}