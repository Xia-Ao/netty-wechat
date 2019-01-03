package com.xiaao.wechat;
/**
 * @author: Xia-ao
 * @create: 2018-12-04 20:57
 **/

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @className: FastDFSImporter
 * @description: NULL
 * @author: Xia-ao
 * @create: 2018-12-04 20:57
 **/
@Configuration
@Import(FdfsClientConfig.class)
// 解决jmx重复注册bean的问题
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class FastDFSImporter {
}
