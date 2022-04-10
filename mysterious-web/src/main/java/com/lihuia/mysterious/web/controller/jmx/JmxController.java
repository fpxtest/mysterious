package com.lihuia.mysterious.web.controller.jmx;

import com.lihuia.mysterious.common.exception.MysteriousException;
import com.lihuia.mysterious.common.response.Response;
import com.lihuia.mysterious.common.response.ResponseCodeEnum;
import com.lihuia.mysterious.common.response.ResponseUtil;
import com.lihuia.mysterious.core.vo.jmx.JmxQuery;
import com.lihuia.mysterious.core.vo.jmx.JmxVO;
import com.lihuia.mysterious.core.vo.page.PageVO;
import com.lihuia.mysterious.service.service.jmx.IJmxService;
import com.lihuia.mysterious.web.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author lihuia.com
 * @date 2022/4/4 2:25 PM
 */

@RestController
@Api(tags = "JMX脚本管理")
@RequestMapping(value = "/jmx")
public class JmxController {

    @Autowired
    private IJmxService jmxService;

    @ApiOperation("上传")
    @PostMapping(value = "/upload")
    public Response<Boolean> uploadJmx(@RequestParam(value = "testCaseId") Long testCaseId,
                                       @RequestParam(value = "jmxFile") MultipartFile jmxFile) {
        return ResponseUtil.buildSuccessResponse(jmxService.uploadJmx(testCaseId, jmxFile, UserUtils.getCurrent()));
    }

    @ApiOperation("更新")
    @PostMapping(value = "/update")
    public Response<Boolean> updateJmx(@RequestBody JmxVO jmxVO) {
        return ResponseUtil.buildSuccessResponse(jmxService.updateJmx(jmxVO, UserUtils.getCurrent()));
    }

    @ApiOperation("删除")
    @GetMapping(value = "/delete")
    public Response<Boolean> deleteJxm(@RequestParam(value = "id") Long id) {
        return ResponseUtil.buildSuccessResponse(jmxService.deleteJmx(id));
    }

    @ApiOperation("分页查询")
    @GetMapping(value = "/list")
    public Response<PageVO<JmxVO>> getJmxList(JmxQuery jmxQuery) {
        return ResponseUtil.buildSuccessResponse(jmxService.getJmxList(jmxQuery));
    }

    @PostMapping(value = "/addOnline")
    public Response<Boolean> addOnlineJmx(@RequestBody JmxVO jmxVO) {
        return ResponseUtil.buildSuccessResponse(jmxService.addOnlineJmx(jmxVO));
    }

    @GetMapping(value = "/getOnline")
    public Response<JmxVO> getOnlineJmx(@RequestParam(value = "id") Long id) {
        return ResponseUtil.buildSuccessResponse(jmxService.getOnlineJmx(id));
    }

    @PostMapping(value = "/updateOnline")
    public Response<Boolean> updateOnlineJmx(@RequestBody JmxVO jmxVO) {
        return ResponseUtil.buildSuccessResponse(jmxService.updateOnlineJmx(jmxVO));
    }

    @GetMapping(value = "/forceDelete")
    public Response<Boolean> forceDeleteJmx(@RequestParam(value = "id") Long id) {
        return ResponseUtil.buildSuccessResponse(jmxService.forceDelete(id));
    }

    @GetMapping(value = "/downloadJmx")
    public void downloadJmx(@RequestParam(value = "id") Long id,
                            @RequestParam(value = "type") Integer type,
                            HttpServletResponse response) {
        JmxVO jmxDO = jmxService.getById(id);
        String fileName = type.equals(1) ? jmxDO.getSrcName() : "debug_" + jmxDO.getSrcName();
        String filePath = jmxDO.getJmxDir() + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            throw new MysteriousException(ResponseCodeEnum.FILE_NOT_EXIST);
        }
        try {
            InputStream inputStream = new FileInputStream(filePath);
            response.reset();
            response.setContentType("bin");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            // 循环取出流中的数据
            byte[] b = new byte[100];
            int len;
            while ((len = inputStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
            inputStream.close();
        } catch (Exception e) {
            throw new MysteriousException(ResponseCodeEnum.DOWNLOAD_ERROR);
        }
    }
}
