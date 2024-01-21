package com.pinting.business.util;

import com.pinting.core.util.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Author:      cyb
 * Date:        2017/5/5
 * Description: 通过字符串运算表达式进行运算
 */
public class CalculatorUtil {

    /* 字符串运算符中的变量名，用于替换 */
    private static String variable = "a";

    private static Logger logger = LoggerFactory.getLogger(CalculatorUtil.class);

    /**
     * 字符串表达式算数运算（仅支持 加 + 减 - 乘 * 除 / 以及 () 操作）
     *
     * @param expression 表达式：变量名统一用 a 表示，用于替换成amounts对应的数据 eg：a+a*a-(a/a+a)
     * @param amounts    每一笔金额，个数必须和表达式中 a 的个数保持一致 eg：2,3,2,5,23,10
     * @return 返回运算结果 eg：2*3+2/(5-23/10)
     */
    public static Double calculate(String expression, Double... amounts) {
        String newExp = getExpression(expression, amounts);

        List<String> postOrder = getPostOrder(getStringList(newExp));   //中缀变后缀
        Stack stack = new Stack();
        for (int i = 0; i < postOrder.size(); i++) {
            if (Character.isDigit(postOrder.get(i).charAt(0))) {
                stack.push(Double.parseDouble(postOrder.get(i)));
            } else {
                Double back = (Double) stack.pop();
                Double front = (Double) stack.pop();
                Double res = 0d;
                switch (postOrder.get(i).charAt(0)) {
                    case '+':
                        res = MoneyUtil.add(front, back).doubleValue();
                        break;
                    case '-':
                        res = MoneyUtil.subtract(front, back).doubleValue();
                        break;
                    case '*':
                        res = MoneyUtil.multiply(front, back).doubleValue();
                        break;
                    case '/':
                        res = MoneyUtil.divide(front, back).doubleValue();
                        break;
                }
                stack.push(res);
            }
        }
        return (Double) stack.pop();
    }


    /**
     * 将字符串转化成List
     *
     * @param str
     * @return
     */
    private static ArrayList<String> getStringList(String str) {
        ArrayList<String> result = new ArrayList<>();
        String num = "";
        int space = 0;
        int index;
        for (int i = 0; i < str.length(); i++) {
            index = i;
            if (Character.isDigit(str.charAt(i))) {
                if (i < space) {
                    continue;
                }
                while (!isDelim(str.charAt(index))) {
                    // 循环直到出现数字运算符
                    num += str.charAt(index);
                    index++;
                    if (str.length() == index) {
                        break;
                    }
                }
                space = index;
            } else {
                if (i < space) {
                    continue;
                }
                if (num != "") {
                    result.add(num);
                }
                result.add(str.charAt(i) + "");
                num = "";
            }
        }
        if (num != "") {
            result.add(num);
        }
        return result;
    }

    /**
     * 中缀表达式 即 人们常用的普通的运算，如：2*3+2/(5-23/10)
     * 将中缀表达式转化为后缀表达式
     * 将中缀表达式转换为后缀表达式的算法思想：
     * ·开始扫描；
     * ·数字时，加入后缀表达式；
     * ·运算符：
     * a. 若为 '('，入栈；
     * b. 若为 ')'，则依次把栈中的的运算符加入后缀表达式中，直到出现'('，从栈中删除'(' ；
     * c. 若为 除括号外的其他运算符， 当其优先级高于除'('以外的栈顶运算符时，直接入栈。否则从栈顶开始，依次弹出比当前处理的运算符优先级高和优先级相等的运算符，直到一个比它优先级低的或者遇到了一个左括号为止。
     * ·当扫描的中缀表达式结束时，栈中的的所有运算符出栈；
     *
     * @param inOrderList 中缀表达式列表
     * @return
     */
    private static ArrayList<String> getPostOrder(ArrayList<String> inOrderList) {
        ArrayList<String> result = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();
        for (int i = 0; i < inOrderList.size(); i++) {
            if (Character.isDigit(inOrderList.get(i).charAt(0))) {
                result.add(inOrderList.get(i));
            } else {
                switch (inOrderList.get(i).charAt(0)) {
                    case '(':
                        stack.push(inOrderList.get(i));
                        break;
                    case ')':
                        while (!stack.peek().equals("(")) {
                            result.add(stack.pop());
                        }
                        stack.pop();
                        break;
                    default:
                        while (!stack.isEmpty() && compare(stack.peek(), inOrderList.get(i))) {
                            result.add(stack.pop());
                        }
                        stack.push(inOrderList.get(i));
                        break;
                }
            }
        }
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    /**
     * 格式转换
     *
     * @param expression 表达式：变量统一用 a 表示，用于替换成amounts对应的数据 eg：a+a*a-(a/a+a)
     * @param amounts    每一笔金额，个数必须和表达式中 a 的个数保持一致 eg：2,3,2,5,23,10
     * @return
     */
    private static String getExpression(String expression, Double... amounts) {
        logger.info("expression = {}; amounts = {}; a的个数：{}; amount的个数：{}", expression, amounts, expression.split(variable, -1).length - 1, amounts.length);
        if (expression.split(variable, -1).length - 1 != amounts.length) {
            throw new RuntimeException("表达式变量个数和传入金额个数不一致");
        }
        StringBuffer buffer = new StringBuffer(expression);
        for (int i = 0; i < amounts.length; i++) {
            if(amounts[i] < 0){
                buffer.replace(buffer.indexOf(variable), buffer.indexOf(variable) + 1, "(0-" + String.valueOf(Math.abs(amounts[i]))+")");
            }else{
                buffer.replace(buffer.indexOf(variable), buffer.indexOf(variable) + 1, String.valueOf(amounts[i]));
            }
        }
        String newExp = buffer.toString();
        return newExp;
    }

    /**
     * 比较运算符等级
     * 栈顶的运算符优先级高于或等于其他运算符，返回true
     *
     * @param peek 栈顶元素
     * @param cur  当前元素
     * @return
     */
    private static boolean compare(String peek, String cur) {
        if ("*".equals(peek) && ("/".equals(cur) || "*".equals(cur) || "+".equals(cur) || "-".equals(cur))) {
            return true;
        } else if ("/".equals(peek) && ("/".equals(cur) || "*".equals(cur) || "+".equals(cur) || "-".equals(cur))) {
            return true;
        } else if ("+".equals(peek) && ("+".equals(cur) || "-".equals(cur))) {
            return true;
        } else if ("-".equals(peek) && ("+".equals(cur) || "-".equals(cur))) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个字符是否为分隔符
     * 表达式中的字符包括：
     * 加“＋”、减“－”、乘“*”、除“/”、左括号“（”、右括号“）”
     */
    private static boolean isDelim(char c) {
        if (("+-*/()".indexOf(c) != -1))
            return true;
        return false;
    }
    private static BigDecimal divide(BigDecimal v1, BigDecimal v2, int scale, int roundingMode) {
		return v1.divide(v2, scale, roundingMode);
	}
    /**
     * 返回#XX.XX的FLOOR处理
     * */
    public static Double formatCash(Double cash , Integer term, Integer scale ) {
    	BigDecimal temp = divide(new BigDecimal(cash), new BigDecimal(term), scale, RoundingMode.FLOOR.ordinal());
    	return temp.doubleValue();
    }
    public static void main(String[] args) {
        // 错误的情况
        System.out.println("============================== 错误 ==================================");
        System.out.println("0.05 + 0.01 = " + (0.05 + 0.01));
        System.out.println("1.0 - 0.42 = " + (1.0 - 0.42));
        System.out.println("4.015 * 100d = " + (4.015 * 100));
        System.out.println("123.3 / 100d = " + (123.3 / 100));
        System.out.println("1.0 - 0.9 = " + (1.0 - 0.9));

        // 正确的情况
        System.out.println("============================== 正确 ==================================");
        System.out.println("0.05 + 0.01 = " + calculate("a+a", 0.05, 0.01));
        System.out.println("1.0 - 0.42 = " + calculate("a-a", 1.0, 0.42));
        System.out.println("4.015 * 100d = " + calculate("a*a", 4.015, 100d));
        System.out.println("123.3 / 100d = " + calculate("a/a", 123.3, 100d));
        System.out.println("1.0 - 0.9 = " + calculate("a-a", 1.0, 0.9));

        System.out.println("============================== 其他验证 ==================================");
        System.out.println(calculate("((a+a)*a-a)/a", 4d, 5d, 2d, 2d, 2d));
        System.out.println(calculate("(a+a)*a", 4d, 5d, 2d));
        System.out.println(calculate("a+a*a", 4d, 5d, 2d));
        System.out.println(calculate("a+a+a", -4d, 5d, 2d));
        System.out.println(calculate("a+a+a", 4d, 5d, -2d));
        System.out.println(calculate("a+a+a", 4d, 5d, -2.1d));
    }
}
