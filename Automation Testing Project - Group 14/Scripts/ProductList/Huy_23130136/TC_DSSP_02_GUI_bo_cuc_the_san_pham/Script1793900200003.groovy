/**
 * Chức năng "Danh sách sản phẩm" - CellphoneS - Nhóm 14
 * SV: Nguyễn Hữu Huy - 23130136
 * Chuyển từ Selenium IDE (23130136_NguyenHuuHuy_ASM07.html, test case "DSSP_02_GUI_bo_cuc_the_san_pham") sang Katalon Groovy.
 * Các hàm PL.* nằm ở Keywords/com/cellphones/productlist/ProductListKeywords.groovy
 */
import com.cellphones.productlist.ProductListKeywords as PL

PL.startSession()
try {
	PL.open('https://cellphones.com.vn/mobile.html')
	PL.store('.product-info-container, .product-info, .product-item', 'cssCard')
	PL.store('.product__price--show, .product-price, .price', 'cssPrice')
	PL.store('samsung', 'brand')
	PL.store('4000', 'pMinK')
	PL.store('7000', 'pMaxK')
	PL.store('4000000', 'pMin')
	PL.store('7000000', 'pMax')
	PL.pause(6000)
	PL.storeEval('Array.prototype.filter.call(window.document.querySelectorAll(\'${cssCard}\'),function(e){return !!(e.offsetWidth||e.offsetHeight||e.getClientRects().length)}).length', 'n0')
	PL.echo('So the san pham hien thi ban dau n0=${n0}')
	PL.storeEval('window.document.querySelectorAll(\'${cssCard} h3, ${cssCard} .product__name\').length', 'nName')
	PL.storeEval('window.document.querySelectorAll(\'${cssCard} img\').length', 'nImg')
	PL.storeEval('window.document.querySelectorAll(\'${cssPrice}\').length', 'nPrice')
	PL.storeEval('(Number(storedVars[\'nName\'] || \'${nName}\') > 0) ? \'PASS\' : \'FAIL: Co ten san pham\'', 'kq5')
	PL.echo('KIEM TRA Co ten san pham: ${kq5}')
	PL.storeEval('(Number(storedVars[\'nImg\'] || \'${nImg}\') > 0) ? \'PASS\' : \'FAIL: Co anh san pham\'', 'kq6')
	PL.echo('KIEM TRA Co anh san pham: ${kq6}')
	PL.storeEval('(Number(storedVars[\'nPrice\'] || \'${nPrice}\') > 0) ? \'PASS\' : \'FAIL: Co gia\'', 'kq7')
	PL.echo('KIEM TRA Co gia: ${kq7}')
	PL.storeEval('(function(){var e=Array.prototype.filter.call(window.document.querySelectorAll(\'${cssCard}\'),function(el){return !!(el.offsetWidth||el.offsetHeight||el.getClientRects().length)});if(!e||e.length===0) return \'FAIL: Khong co the san pham\';var w=e[0].offsetWidth,ok=true;e.forEach(function(el){if(Math.abs(el.offsetWidth-w)>5)ok=false});return ok?\'PASS\':\'FAIL: Cac the khong cung kich thuoc\'})()', 'kq8')
	PL.echo('KIEM TRA Cac the cung kich thuoc: ${kq8}')
	PL.storeEval('(function(){var imgs=Array.from(window.document.querySelectorAll(\'${cssCard} img\')).slice(0,4);if(!imgs||imgs.length===0) return \'FAIL: Khong tim thay anh\';var ok=true;imgs.forEach(function(el){if(!el.src||(el.complete && el.naturalWidth===0))ok=false});return ok?\'PASS\':\'FAIL: 4 anh dau tai khong thanh cong\'})()', 'kq9')
	PL.echo('KIEM TRA 4 anh dau tai thanh cong: ${kq9}')
} finally {
	PL.endSession()
}
