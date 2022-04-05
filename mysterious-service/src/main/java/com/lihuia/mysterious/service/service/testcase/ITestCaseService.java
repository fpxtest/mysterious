package com.lihuia.mysterious.service.service.testcase;

import com.lihuia.mysterious.core.entity.jmx.JmxDO;
import com.lihuia.mysterious.core.entity.testcase.TestCaseDO;
import com.lihuia.mysterious.core.vo.page.PageVO;
import com.lihuia.mysterious.core.vo.testcase.TestCaseElementVO;
import com.lihuia.mysterious.core.vo.testcase.TestCaseFullVO;
import com.lihuia.mysterious.core.vo.testcase.TestCaseQuery;
import com.lihuia.mysterious.core.vo.testcase.TestCaseVO;
import com.lihuia.mysterious.core.vo.user.UserVO;

import java.util.List;

/**
 * @author lihuia.com
 * @date 2022/4/1 下午3:14
 */

public interface ITestCaseService {

    /**
     * 新增压测用例
     * @param testCaseVO
     * @param userVO
     * @return
     */
    Long addTestCase(TestCaseVO testCaseVO, UserVO userVO);

    /**
     * 清理压测用例
     * @param id
     * @return
     */
    Boolean deleteTestCase(Long id);

    /**
     * 批量清理压测用例
     * @param ids
     * @return
     */
    Boolean batchDeleteTestCase(List<Long> ids);

    /**
     * 更新压测用例
     * @param testCaseVO
     * @param userVO
     * @return
     */
    Boolean updateTestCase(TestCaseVO testCaseVO, UserVO userVO);

    /**
     * 分页查询用例列表
     * @param testCaseQuery
     * @return
     */
    PageVO<TestCaseVO> getTestCaseList(TestCaseQuery testCaseQuery);

    /**
     * 查询用例信息
     * @param id
     * @return
     */
    TestCaseVO getById(Long id);

    /**
     * 根据用例查询关联的jmx，jar，csv信息
     * @param id
     * @return
     */
    TestCaseElementVO getElement(Long id);

    /**
     * 查询用例的基本信息和关联的jmx，jar，csv信息
     * @param id
     * @return
     */
    TestCaseFullVO getFull(Long id);

    /**
     * 调试压测用例
     * @param id
     * @param userVO
     * @return
     */
    Boolean debugTestCase(Long id, UserVO userVO);

    /**
     * 执行压测用例
     * @param id
     * @param userVO
     * @return
     */
    Boolean runTestCase(Long id, UserVO userVO);

    /**
     * 停止压测用例
     * @param id
     * @param userVO
     * @return
     */
    Boolean stopTestCase(Long id, UserVO userVO);

    /**
     * 修改用例执行状态
     * @param id
     * @param status
     * @return
     */
    Boolean updateStatus(Long id, Integer status);

    /**
     * 查询关联的jmx脚本
     * @param id
     * @return
     */
    JmxDO getJmx(Long id);

    /**
     * 查询指定状态的用例
     * @param status
     * @return
     */
    List<TestCaseDO> getByStatus(Integer status);

    /**
     * 将测试用例的所有依赖，同步到指定的slave节点上
     * @param testCaseId
     * @param nodeId
     * @return
     */
    Boolean syncNodeElement(Long testCaseId, Long nodeId);

    /**
     * 将所有测试用例的所有依赖，同步到指定的slave节点上
     * @param nodeId
     * @return
     */
    Boolean syncAllNodeElement(Long nodeId);
}