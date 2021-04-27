package sample;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * 第二代居民身份证阅读器<br>
 * 用户信息各字段的长度定义 （单位：字节） 姓名 30 性别 2 民族 4 出生日期 16 住址 70 身份号码 36 签发机关 30
 */
public class IDCardReader {
    public interface libTermb extends Library {
        libTermb instance = (libTermb) Native.loadLibrary("termb", libTermb.class);
        /**
         * 初始化串口;
         *
         * @param Port 设置串口
         * @return 1 正确 卡片正确放置时 其它 错误 未放卡或卡片放置不正确时
         */
        public int InitComm(int Port);

        /**
         * 关闭串口;
         *
         * @return
         */
        public int CloseComm();

        /**
         * 卡认证
         *
         * @return
         */
        public int Authenticate();

        /**
         * 通过读卡器从非接触卡中读取相应信息
         *
         * @param Active 读取信息类型 1 读基本信息 形成文字信息文件WZ.TXT、相片文件XP.WLT和ZP.BMP 2 只读文字信息
         *               形成文字信息文件WZ.TXT和相片文件XP.WLT 3 读最新住址信息 形成最新住址文件NEWADD.TXT 5
         *               读芯片管理号 形成二进制文件IINSNDN.bin
         * @return 1 正确 0 读卡错误 -1 相片解码错误 -2 wlt文件后缀错误 -3 wlt文件打开错误 -4 wlt文件格式错误
         * -5 软件未授权-6 设备连接失败
         */
        public int Read_Content(int Active);

        /**
         * 读取 SAM 模块的编号，成功返回模块SAMID编号
         */
        public String GetSAMID();

        /**
         * 读取二代证芯片信息成功后，读取姓名，成功返回姓名字符串。
         *
         * @return
         */
        public String GetName();

        /**
         * 读取二代证芯片信息成功后，读取性别
         *
         * @return
         */
        public String GetSex();

        /**
         * 读取二代证芯片信息成功后，读取民族
         *
         * @return
         */
        public String GetNation();

        /**
         * 读取出生日期，格式（yyyymmdd）
         *
         * @return
         */
        public String GetBornDate();

        /**
         * 读取详址
         *
         * @return
         */
        public String GetAddress();

        /**
         * 读取证件号码
         *
         * @return
         */
        public String GetIDNo();

        /**
         * 读取签发机关
         *
         * @return
         */
        public String GetSignGov();

        /**
         * 读取证件起始日期，格式yyyymmdd
         *
         * @return
         */
        public String GetStartDate();

        /**
         * 读取证件截止日期，格式yyyymmdd
         *
         * @return
         */
        public String GetEndDate();

        /**
         * 生成身份证正面图片文件
         *
         * @param sHead
         * @param sImage
         * @return
         */
        public Boolean MakeIDCardImage(String sHead, String sImage);

        /**
         * 读取外国人永久居留身份证英文姓名
         */
        public String GetFPRENName();

        /**
         * 读取外国人永久居留身份证性别
         *
         * @return
         */
        public String GetFPRSEX();

        /**
         * 读取外国人永久居留身份证证件号码
         *
         * @return
         */
        public String GetFPRIDNo();

        /**
         * 读取外国人永久居留身份证国籍代码
         *
         * @return
         */
        public String GetFPRNationCode();

        /**
         * 读取外国人永久居留身份证中文姓名
         *
         * @return
         */
        public String GetFPRCHNName();

        /**
         * 读取外国人永久居留身份证证件签发时间
         *
         * @return
         */
        public String GetFPRValidStartDate();

        /**
         * 读取外国人永久居留身份证证件终止时间
         *
         * @return
         */
        public String GetFPRValidEndDate();

        /**
         * 读取外国人永久居留身份证出生日期
         *
         * @return
         */
        public String GetFPRBirthday();

        /**
         * 读取外国人永久居留身份证证件版本号
         *
         * @return
         */
        public String GetFPRIDVersion();

        /**
         * 读取外国人永久居留身份证当次申请受理机关代码
         *
         * @return
         */
        public String GetFPRIssuingAuthorityCode();

        /**
         * 读取外国人永久居留身份证证件类型标识
         *
         * @return
         */
        public String GetFPRIDType();

        /**
         * 读取外国人永久居留身份证预留项
         *
         * @return
         */
        public String GetFPRReserve();

        /**
         * 判断是否外国人永久居留证（是返回1，不是返回0）
         *
         * @return
         */
        public int IsFPRIDCard();

        /**
         * 本函数用于通过读卡器从非接触身份证卡中读取相应信息，信息存储在由 cPath参数指定的路径下
         *
         * @param cPath  信息存储路径，路径长度不能大于 240
         * @param Active 读取信息类型
         * @return
         */
        int Read_Content_Path(String cPath, int Active);

    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        int ret = 1;
        if (ret > 0) {
            System.out.println("设置路径成功");
        }
        ret = libTermb.instance.InitComm(1001);
        System.out.println("初始化端口:" + (ret == 0 ? "成功" : "失败-->" + ret));
        if(ret != 0) {
            libTermb.instance.CloseComm();
            return;
        }

        ret = libTermb.instance.Authenticate();
        System.out.println("卡认证:" + (ret == 0 ? "成功" : "失败-->" + ret));
        if(ret != 0) {
            libTermb.instance.CloseComm();
            return;
        }

        ret = libTermb.instance.Read_Content(1);
        System.out.println("读卡信息:" + (ret == 0 ? "成功" : "失败-->" + ret));
        if(ret != 0) {
            libTermb.instance.CloseComm();
            return;
        }

        int flag = libTermb.instance.IsFPRIDCard();
        if (flag == 0) {
            System.out.println("识别卡片为居民身份证！");
            System.out.println(libTermb.instance.GetAddress());
            System.out.println(libTermb.instance.GetName());
            System.out.println(libTermb.instance.GetSex());
            System.out.println(libTermb.instance.GetNation());
            System.out.println(libTermb.instance.GetBornDate());
            System.out.println(libTermb.instance.GetAddress());
            System.out.println(libTermb.instance.GetIDNo());
            System.out.println(libTermb.instance.GetSignGov());
            System.out.println(libTermb.instance.GetStartDate());
            System.out.println(libTermb.instance.GetEndDate());
            System.out.println(libTermb.instance.MakeIDCardImage("C:\\zp.bmp", "C:\\image.bmp").toString());
        } else if (flag == 1) {
            System.out.println("识别卡片为外国人永久居留证！");
            System.out.println(libTermb.instance.GetFPRENName());
            System.out.println(libTermb.instance.GetFPRSEX());
            System.out.println(libTermb.instance.GetFPRIDNo());
            System.out.println(libTermb.instance.GetFPRNationCode());
            System.out.println(libTermb.instance.GetFPRCHNName());
            System.out.println(libTermb.instance.GetFPRValidStartDate());
            System.out.println(libTermb.instance.GetFPRValidEndDate());
            System.out.println(libTermb.instance.GetFPRBirthday());
            System.out.println(libTermb.instance.GetFPRIDVersion());
            System.out.println(libTermb.instance.GetFPRIssuingAuthorityCode());
            System.out.println(libTermb.instance.GetFPRIDType());
            System.out.println(libTermb.instance.GetFPRReserve());
        }
        libTermb.instance.CloseComm();
    }
}
