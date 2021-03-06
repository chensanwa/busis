package com.grad.controller;

import com.grad.entity.Location;
import com.grad.service.ILocationService;
import com.grad.util.ApiFormatUtil;
import com.grad.vo.LocationApiVo;
import com.grad.vo.LocationListApiVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @program: busis
 * @description: 位置收藏相关接口Controller
 * @author: Mr.Chen
 * @create: 2018-03-14 21:22
 **/
@Controller
@RequestMapping(value = "/location")
public class LocationController {

    @Resource
    private ILocationService locationService;

    /**
     * 添加收藏位置信息
     * @param location  位置基本信息
     *          location_name (varchar(20)): 位置名称
     *          location_longitude (varchar(15)): 经度(必填)
     *          location_latitude (varchar(15)): 纬度(必填)
     *          location_type (varchar(15)): 位置类型
     *          area (varchar(20)): 区域
     *          user_id(int):user ID(必填)
     * @return  新增位置收藏ID
     * @throws Exception
     */
    @RequestMapping(value = "/add",method = {RequestMethod.POST,RequestMethod.GET},produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String addLocation(Location location) throws Exception{
        int result = -1;
        LocationApiVo locationApiVo = new LocationApiVo();

        locationApiVo = locationService.addLocation(location);

        if (locationApiVo.getLocation() != null){
            result = locationApiVo.getLocation().getLocation_id();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",result);

        String resultJson = jsonObject.toString();

        return ApiFormatUtil.apiFormat(locationApiVo.getCode(),locationApiVo.getMessage(),resultJson);
    }


    /**
     * 删除收藏位置
     * @param location_id  待删除位置ID
     * @throws Exception
     */
    @RequestMapping(value = "/delete",method = {RequestMethod.POST,RequestMethod.GET},produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String deleteLocation(int location_id) throws Exception{
        LocationApiVo locationApiVo = locationService.deleteLocation(location_id);
        return ApiFormatUtil.apiFormat(locationApiVo.getCode(),locationApiVo.getMessage()," ");
    }


    /**
     * 通过user_id查询用户收藏地点
     * @param user_id
     * @return  位置基本信息列表
     *          location_id (int,PK): 位置ID
     *          location_name (varchar(20)): 位置名称
     *          location_longitude (varchar(15)): 经度
     *          location_latitude (varchar(15)): 纬度
     *          location_type (varchar(15)): 位置类型
     *          area (varchar(20)): 区域
     *          user_id(int):user ID
     * @throws Exception
     */
    @RequestMapping(value = "/query",method = {RequestMethod.POST,RequestMethod.GET},produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String findLocationByUser_id(int user_id) throws Exception{

        LocationListApiVo locationList = locationService.findLocationByUser_id(user_id);

        JSONArray result = JSONArray.fromObject(locationList.getLocationList());

        String resultJson = result.toString();

        return ApiFormatUtil.apiFormat(locationList.getCode(),locationList.getMessage(),resultJson);
    }


    /**
     * 统计该位置被多少用户收藏
     * @param location 位置信息
     *          location_id (int,PK): 位置ID
     *          location_name (varchar(20)): 位置名称
     *          location_longitude (varchar(15)): 经度（必填）
     *          location_latitude (varchar(15)): 纬度（必填）
     *          location_type (varchar(15)): 位置类型
     *          area (varchar(20)): 区域
     *          user_id(int):user ID
     * @return 查询结果，若输入数据非法，则返回-1
     * @throws Exception
     */
    @RequestMapping(value = "/total",method = {RequestMethod.POST,RequestMethod.GET},produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String totalLocation(Location location) throws Exception{

        int result = 0;

        result = locationService.totalLocation(location);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result",result);

        String resultJson = jsonObject.toString();

        return ApiFormatUtil.apiFormat(1,"查询成功！",resultJson);
    }




}
