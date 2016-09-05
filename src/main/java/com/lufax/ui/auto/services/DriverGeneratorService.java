package com.lufax.ui.auto.services;

import com.lufax.ui.auto.components.DeviceInfoAccessor;
import com.lufax.ui.auto.components.PackageInfoAccessor;
import com.lufax.ui.auto.interfaces.LuCapabilityType;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 根据获取到的设备信息生成Appium驱动
 * Created by jiangcan on 16/8/4.
 */

@Service
public class DriverGeneratorService {

    @Autowired
    public DeviceInfoAccessor deviceInfoAccessor;
    @Autowired
    public PackageInfoAccessor packageInfoAccessor;

    public DesiredCapabilities capabilities = new DesiredCapabilities();
    public JSONObject devInfo = null;
    public String appPath = null;

    public DriverGeneratorService setLuCapabilities() throws IOException {
        devInfo = deviceInfoAccessor.getDeviceInfo();
        capabilities.setCapability(LuCapabilityType.PLATFORM_NAME, devInfo.getString(LuCapabilityType.PLATFORM_NAME));
        capabilities.setCapability(LuCapabilityType.PLATFORM_VERSION, devInfo.getString(LuCapabilityType.PLATFORM_VERSION));
        capabilities.setCapability(LuCapabilityType.DEVICE_NAME, devInfo.getString(LuCapabilityType.DEVICE_NAME));
        capabilities.setCapability(LuCapabilityType.UDID, devInfo.getString(LuCapabilityType.UDID));
        capabilities.setCapability(LuCapabilityType.APP_PATH,packageInfoAccessor.getPackageInfo().get(LuCapabilityType.APP_PATH));
        capabilities.setCapability(LuCapabilityType.NO_RESET, true);
        return this;
    }

    public AppiumDriver getAppiumDriver() throws MalformedURLException {
        if("android".equalsIgnoreCase(this.capabilities.getCapability(LuCapabilityType.PLATFORM_NAME).toString())){
            return new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), this.capabilities);
        }else{
            return new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"),this.capabilities);
        }
    }
}