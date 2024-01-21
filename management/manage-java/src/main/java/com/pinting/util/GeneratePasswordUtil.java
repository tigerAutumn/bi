package com.pinting.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 按一定的概率生成一个随机的N位(N>=8)密码，必须由字母数字特殊符号组成，三者缺一不可
 * 生成规则：8位密码（必须包含数字+字母+特殊符号，区分大小写）特殊字符支持：_!@#$
 * <ul>
 * <li>数字: 0-9</li>
 * <li>字母: A-Za-z</li>
 * <li>特殊符号: _!@#$</li>
 * </ul>
 */
public class GeneratePasswordUtil {

    private static final byte INDEX_NUMBER = 0; // 数字
    private static final byte INDEX_LETTER = 1; // 字母
    private static final byte INDEX_SPECIAL_CHAR = 2; // 特殊字符
    private static final byte INDEX_CAPITAL_LETTER = 3; // 大写字母
    private static final byte INDEX_SMALL_LETTER = 4; // 小写字母

    /**
     * 特殊符号
     */
    private static final char[] SPECIAL_CHARS = {'!', '@', '#', '$', '_'};

    /*
     * 正则校验包含数字，大写字母，小写字母，特殊字符
     * @param str 传入的字符串
     * @return 是包含返回true,否则返回false
     */
    public static boolean checkStr(String str) {
        // 判断密码是否包含数字：包含返回1，不包含返回0
        int i = str.matches(".*\\d+.*") ? 1 : 0;
        // 判断密码是否包含小写字母：包含返回1，不包含返回0
        int j = str.matches(".*[a-z]+.*") ? 1 : 0;
        // 判断密码是否包含大写字母：包含返回1，不包含返回0
        int r = str.matches(".*[A-Z]+.*") ? 1 : 0;
        // 判断密码是否包含特殊符号(_!@#$)：包含返回1，不包含返回0
        int k = str.matches(".*[_!@#$]+.*") ? 1 : 0;
        // 必填字母数字特殊字符
        if (i + j + r + k < 4) {
            return false;
        }
        return true;
    }

    /*
     * 正则校验str 值必须是数字，字母，特殊字符_!@#$
     * @param str 传入的字符串
     * @return 格式验证通过返回true,否则返回false
     */
    public static boolean checkPassword(String str) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_!@#$]*$");
        Matcher check = pattern.matcher(str);
        if (!check.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 生成8位管理台密码
     * 3位数字，0位字母（不分大小写），1位特殊字符，1位大写字母，3位小写字母
     *
     * @return
     */
    public static String generatePassword() {
        byte[] genChances = {3, 0, 1, 1, 3};
        return String.valueOf(generateRandomPassword(8, genChances));
    }

    /**
     * 按一定的概率生成一个随机的N位(N>=3)密码，必须由字母数字特殊符号组成，三者缺一不可
     *
     * @param len             密码长度,必须大于等于3
     * @param paramGenChances 分别是生成数字、字母、特殊符号的概率
     * @return 生成的随机密码
     */
    private static char[] generateRandomPassword(final int len, final byte[] paramGenChances) {
        char[] password = new char[len];
        // 之所以该复制一份是为了使函数不对外产生影响
        byte[] genChances = paramGenChances.clone();
        byte[] genNums = new byte[genChances.length];
        for (byte i = 0; i < genChances.length; i++) {
            genNums[i] = 0;
        }
        Random random = new Random();
        int r;
        for (int i = 0; i < len; i++) {
            byte index = getPasswordCharType(random, genChances);
            genNums[index]++;
            switch (index) {
                case INDEX_NUMBER:
                    password[i] = (char) ('0' + random.nextInt(10));
                    break;
                case INDEX_LETTER:
                    r = random.nextInt(52);
                    if (r < 26) {
                        password[i] = (char) ('A' + r);
                    } else {
                        password[i] = (char) ('a' + r - 26);
                    }
                    break;
                case INDEX_SPECIAL_CHAR:
                    r = random.nextInt(SPECIAL_CHARS.length);
                    password[i] = SPECIAL_CHARS[r];
                    break;
                case INDEX_CAPITAL_LETTER:
                    password[i] = (char) ('A' + random.nextInt(26));
                    break;
                case INDEX_SMALL_LETTER:
                    password[i] = (char) ('a' + random.nextInt(26));
                    break;
                default:
                    password[i] = ' ';
                    break;
            }
        }
        return password;
    }

    /**
     * 获取该密码字符的类型
     *
     * @param random     随机数生成器
     * @param genChances 分别是生成数字、字母、特殊符号的概率
     * @return 密码字符的类型
     */
    private static byte getPasswordCharType(Random random, byte[] genChances) {
        int total = 0; // 指定生成字符长度
        byte i = 0;
        int r = 0;
        for (; i < genChances.length; i++) {
            if (genChances[i] > 0) {
                total += genChances[i];
            }
        }

        if (total > 0) {
            r = random.nextInt(total);
            for (i = 0; i < genChances.length; i++) {
                r = r - genChances[i];
                if (r < genChances[i]) {
                    break;
                }
            }

            if (genChances[i] > 0) {
                genChances[i]--;
                return i;
            } else {
                for (i = 0; i < genChances.length; i++) {
                    if (genChances[i] > 0) {
                        break;
                    }
                }
                genChances[i]--;
                return i;
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            String passwd = generatePassword();
            if (!GeneratePasswordUtil.checkPassword(passwd)) {
                System.out.println(passwd);
            }
        }

        System.out.println("true");
//        System.out.println(GeneratePasswordUtil.checkPassword("sdfg123sdg#"));
    }
}
