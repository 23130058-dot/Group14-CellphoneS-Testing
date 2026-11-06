
/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */

import java.lang.String



def static "com.cellphones.SearchKeywords.pageText"() {
    (new com.cellphones.SearchKeywords()).pageText()
}


def static "com.cellphones.SearchKeywords.countProducts"() {
    (new com.cellphones.SearchKeywords()).countProducts()
}


def static "com.cellphones.SearchKeywords.isErrorPage"() {
    (new com.cellphones.SearchKeywords()).isErrorPage()
}


def static "com.cellphones.SearchKeywords.closePopups"() {
    (new com.cellphones.SearchKeywords()).closePopups()
}


def static "com.cellphones.SearchKeywords.waitResults"() {
    (new com.cellphones.SearchKeywords()).waitResults()
}


def static "com.cellphones.SearchKeywords.openHome"() {
    (new com.cellphones.SearchKeywords()).openHome()
}


def static "com.cellphones.SearchKeywords.search"(
    	String keyword	
     , 	boolean useButton	) {
    (new com.cellphones.SearchKeywords()).search(
        	keyword
         , 	useButton)
}

 /** Click nút/biểu tượng kính lúp nằm cạnh ô tìm kiếm (không phụ thuộc class cụ thể của nút). */ 
def static "com.cellphones.SearchKeywords.clickSearchButton"() {
    (new com.cellphones.SearchKeywords()).clickSearchButton()
}


def static "com.cellphones.SearchKeywords.clickFirstProduct"() {
    (new com.cellphones.SearchKeywords()).clickFirstProduct()
}


def static "com.cellphones.SearchKeywords.clickLoadMore"() {
    (new com.cellphones.SearchKeywords()).clickLoadMore()
}


def static "com.cellphones.SearchKeywords.searchBoxValue"() {
    (new com.cellphones.SearchKeywords()).searchBoxValue()
}


def static "com.cellphones.SearchKeywords.verifyResults"(
    	String token	) {
    (new com.cellphones.SearchKeywords()).verifyResults(
        	token)
}


def static "com.cellphones.SearchKeywords.verifyNoAlert"() {
    (new com.cellphones.SearchKeywords()).verifyNoAlert()
}


def static "com.cellphones.SearchKeywords.search"(
    	String keyword	) {
    (new com.cellphones.SearchKeywords()).search(
        	keyword)
}


def static "com.cellphones.SearchRunner.run"(
    	String tcId	
     , 	String keyword	
     , 	String token	
     , 	String mode	) {
    (new com.cellphones.SearchRunner()).run(
        	tcId
         , 	keyword
         , 	token
         , 	mode)
}


def static "com.cellphones.productlist.ProductListKeywords.startSession"() {
    (new com.cellphones.productlist.ProductListKeywords()).startSession()
}


def static "com.cellphones.productlist.ProductListKeywords.endSession"() {
    (new com.cellphones.productlist.ProductListKeywords()).endSession()
}


def static "com.cellphones.productlist.ProductListKeywords.open"(
    	String url	) {
    (new com.cellphones.productlist.ProductListKeywords()).open(
        	url)
}


def static "com.cellphones.productlist.ProductListKeywords.pause"(
    	int millis	) {
    (new com.cellphones.productlist.ProductListKeywords()).pause(
        	millis)
}


def static "com.cellphones.productlist.ProductListKeywords.waitForElementPresentXpath"(
    	String xpath	
     , 	int millis	) {
    (new com.cellphones.productlist.ProductListKeywords()).waitForElementPresentXpath(
        	xpath
         , 	millis)
}


def static "com.cellphones.productlist.ProductListKeywords.assertTextPresent"(
    	String text	) {
    (new com.cellphones.productlist.ProductListKeywords()).assertTextPresent(
        	text)
}


def static "com.cellphones.productlist.ProductListKeywords.echo"(
    	String text	) {
    (new com.cellphones.productlist.ProductListKeywords()).echo(
        	text)
}


def static "com.cellphones.productlist.ProductListKeywords.store"(
    	String value	
     , 	String varName	) {
    (new com.cellphones.productlist.ProductListKeywords()).store(
        	value
         , 	varName)
}

 /** Chay bieu thuc JavaScript tren trang hien tai, tra ve ket qua dang chuoi (giong storeEval). */ 
def static "com.cellphones.productlist.ProductListKeywords.evalJs"(
    	String expression	) {
    (new com.cellphones.productlist.ProductListKeywords()).evalJs(
        	expression)
}


def static "com.cellphones.productlist.ProductListKeywords.storeEval"(
    	String expression	
     , 	String varName	) {
    (new com.cellphones.productlist.ProductListKeywords()).storeEval(
        	expression
         , 	varName)
}


def static "com.cellphones.productlist.ProductListKeywords.verifyEval"(
    	String expression	
     , 	String expected	) {
    (new com.cellphones.productlist.ProductListKeywords()).verifyEval(
        	expression
         , 	expected)
}
