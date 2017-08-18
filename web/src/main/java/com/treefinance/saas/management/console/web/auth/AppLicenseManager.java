/*
 * Copyright © 2015 - 2017 杭州大树网络技术有限公司. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.treefinance.saas.management.console.web.auth;

import com.treefinance.saas.management.console.biz.service.AppLicenseService;
import com.treefinance.saas.management.console.common.domain.dto.AppLicenseDTO;
import com.treefinance.saas.management.console.common.exceptions.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jerry
 * @since 17:59 25/04/2017
 */
@Component
public class AppLicenseManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(AppLicenseManager.class);

  @Autowired
  private AppLicenseService appLicenseService;

  public AppLicenseDTO getAppLicense(String appId) throws ForbiddenException {
    AppLicenseDTO license = appLicenseService.selectOneByAppId(appId);
    if (license == null) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Can not find license for app '{}'.", appId);
      }
      throw new ForbiddenException("Can not find license for app '" + appId + "'.");
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Found license[{}] for app '{}'.", license.getId(), appId);
    }
    return license;
  }
}
