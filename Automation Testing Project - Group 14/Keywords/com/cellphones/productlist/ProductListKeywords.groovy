package com.cellphones.productlist

import java.util.regex.Matcher
import java.util.regex.Pattern

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * Nhom 14 - CellphoneS - Keywords dung chung cho cac test case "Danh sach san pham" (DSSP).
 * Cac test case nay duoc chuyen tu Selenium IDE (.html: open / pause / store / storeEval / verifyEval / echo ...)
 * sang Groovy cua Katalon. Moi lenh Selenium IDE tuong ung 1 ham ben duoi, giu nguyen y nghia:
 *   - ${ten_bien} trong tham so duoc thay bang gia tri da luu (store / storeEval)
 *   - verifyEval: sai thi danh dau FAILED nhung van chay tiep (giong "verify" cua Selenium IDE)
 *   - assertTextPresent: sai thi dung test case
 */
public class ProductListKeywords {

	static final Pattern VAR = Pattern.compile('\\$\\{(\\w+)\\}')

	/** Bien da luu (tuong duong storedVars cua Selenium IDE), reset o dau moi test case. */
	static Map<String, String> vars = [:]

	/** Thay ${ten} bang gia tri da luu; bien chua co thi giu nguyen. */
	static String sub(String text) {
		Matcher m = VAR.matcher(text)
		StringBuilder sb = new StringBuilder()
		int last = 0
		while (m.find()) {
			sb.append(text.substring(last, m.start()))
			String name = m.group(1)
			sb.append(vars.containsKey(name) ? vars.get(name) : m.group(0))
			last = m.end()
		}
		sb.append(text.substring(last))
		return sb.toString()
	}

	@Keyword
	static void startSession() {
		vars.clear()
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
	}

	@Keyword
	static void endSession() {
		WebUI.closeBrowser()
	}

	@Keyword
	static void open(String url) {
		WebUI.navigateToUrl(sub(url))
		WebUI.waitForPageLoad(30)
	}

	@Keyword
	static void pause(int millis) {
		WebUI.delay((int) Math.ceil(millis / 1000.0d))
	}

	@Keyword
	static void waitForElementPresentXpath(String xpath, int millis) {
		TestObject to = new TestObject('xpath_' + xpath.hashCode())
		to.addProperty('xpath', ConditionType.EQUALS, xpath)
		WebUI.waitForElementPresent(to, (int) Math.ceil(millis / 1000.0d))
	}

	@Keyword
	static void assertTextPresent(String text) {
		WebUI.verifyTextPresent(sub(text), false)
	}

	@Keyword
	static void echo(String text) {
		KeywordUtil.logInfo(sub(text))
	}

	@Keyword
	static void store(String value, String varName) {
		vars.put(varName, sub(value))
	}

	/** Chay bieu thuc JavaScript tren trang hien tai, tra ve ket qua dang chuoi (giong storeEval). */
	@Keyword
	static String evalJs(String expression) {
		Object result = WebUI.executeJavaScript('return String((' + sub(expression) + '));', null)
		return result == null ? 'null' : result.toString()
	}

	@Keyword
	static void storeEval(String expression, String varName) {
		vars.put(varName, evalJs(expression))
	}

	@Keyword
	static void verifyEval(String expression, String expected) {
		String actual = evalJs(expression)
		String exp = sub(expected)
		KeywordUtil.logInfo("verifyEval: '${actual}' (mong doi '${exp}')")
		WebUI.verifyEqual(actual, exp, FailureHandling.CONTINUE_ON_FAILURE)
	}
}
