package com.kingsoft.sdktest.unittest

import com.kingsoft.KSO.stat.KSOStat
import com.kingsoft.sdktest.base.BaseTestCase
import com.kingsoft.sendemail.JavaMailSender
import com.kingsoft.sendemail.MailSenderInfo
import org.robolectric.annotation.Config
import spock.lang.Shared

/**
 * Created by jianan on 15/10/12.
 */
@Config(emulateSdk = 18)
class sdkManualUploadTest extends BaseTestCase {
    @Shared
    def CID = 6
    def SUB_CID = "TEST_MAIL"
    def CHANAL_NAME = "DEBUG"
    //执行次数
    def EXTNUM = 1000
    //执行间隔时间
    def seconds = 1

    /**
     * 测试前提条件设置针对测试类中的所有测试方法
     */
    def setup() {
        super.init();
        KSOStat.setLoggable(true);
        KSOStat.KSOStatInit(mApplication);
    }

    /**
     * 测试后置处理方法针对测试类中的所有测试方法
     */
    def cleanup() {

    }

    /**
     * 测试方法
     * 可以在“”中间添加描述信息作为该测试方法名称
     * @param time
     */
    def "test manual upload data"() {
        setup:
        def result = false;
        def failNum = 0;
        when:
        def i = 0;
        for (; i < EXTNUM; i++) {
            result = KSOStat.manualUploadData(CID, SUB_CID, getUUID(), null, CHANAL_NAME)
            System.out.println("UploadData" + result)
            if (result == false) {
                failNum++
            }
            sleep(seconds * 1000)
        }
        if (failNum != 0) {
            sendEmail(failNum);
        }

        then:
        assert failNum == 0


    }

    /**
     * 发送邮件
     * @param time
     */
    private void sendEmail(time) {
        def error = time + " times manual upload data failed !!! "
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.126.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("WpsMail_test_jn@126.com");
        mailInfo.setPassword("1234567aaa");//您的邮箱密码
        mailInfo.setFromAddress("WpsMail_test_jn@126.com");
        mailInfo.setToAddress("jianan@kingsoft.com");
        String msg = error.toString().replace("\n", "<br>")
        msg.replaceAll("\t", "&nbsp;")
        mailInfo.setSubject("manual upload data failed");
        mailInfo.setContent(msg);
        JavaMailSender sms = new JavaMailSender();
        sms.sendHtmlMail(mailInfo);//发送html格
    }

    /**
     * 获得一个UUID
     * @return String UUID
     */
    private String getUUID() {
        return java.util.UUID.randomUUID().toString();
    }



}
