package com.lufax.ui.auto.components;

import com.lufax.ui.auto.interfaces.LuCapabilityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Jc on 16/8/6.
 * 获取测试程序包信息工具类
 * 该实例只能从spring容器中获取
 */

@Component
public class PackageInfoAccessor {

    @Autowired
    private PropertiesCenter propertiesCenter;

    private String packageDir = System.getProperty("user.dir") + String.format("%spackages", File.separator);
    private HashMap<String,String> packInfo = new HashMap<String,String>();

    public HashMap<String,String> getPackageInfo() throws IOException {
        String mobileOSType = propertiesCenter.init().getRunConfigs().get("mobile.os.type");
        String packageName = propertiesCenter.init().getRunConfigs().get("package.name");
        String packageVersion = propertiesCenter.init().getRunConfigs().get("package.version");
        String appUrl = "";
        if ("android".equalsIgnoreCase(mobileOSType)) {
            appUrl = packageDir + String.format("%s%s%s%s", File.separator, "Android", File.separator, packageName);
        } else {
            appUrl = packageDir + String.format("%s%s%s%s", File.separator, "iOS", File.separator, packageName);
        }
        packInfo.put(LuCapabilityType.APP_PATH, appUrl);
        packInfo.put(LuCapabilityType.PACKAGE_VERSION, packageVersion);
        return packInfo;
    }
}
