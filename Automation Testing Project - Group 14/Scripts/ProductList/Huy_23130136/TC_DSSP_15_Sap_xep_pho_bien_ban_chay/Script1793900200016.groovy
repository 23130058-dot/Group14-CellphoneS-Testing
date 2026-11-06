/**
 * Chức năng "Danh sách sản phẩm" - CellphoneS - Nhóm 14
 * SV: Nguyễn Hữu Huy - 23130136
 * Chuyển từ Selenium IDE (23130136_NguyenHuuHuy_ASM07.html, test case "DSSP_15_Sap_xep_pho_bien_ban_chay") sang Katalon Groovy.
 * Các hàm PL.* nằm ở Keywords/com/cellphones/productlist/ProductListKeywords.groovy
 */
import com.cellphones.productlist.ProductListKeywords as PL

PL.startSession()
try {
	PL.open('https://cellphones.com.vn/mobile.html')
	PL.store('.product-info-container, .product-info, .product-item', 'cssCard')
	PL.store('.product__price--show, .product-price, .price', 'cssPrice')
	PL.pause(6000)
	PL.storeEval('Array.prototype.filter.call(window.document.querySelectorAll(\'${cssCard}\'),function(e){return !!(e.offsetWidth||e.offsetHeight||e.getClientRects().length)}).length', 'n0')
	PL.echo('So the san pham hien thi ban dau n0=${n0}')
	PL.storeEval('(function(txt){var els=Array.from(window.document.querySelectorAll(\'button, div, span, a, label, p\'));for(var i=0;i<els.length;i++){var e=els[i];var t=(e.innerText||e.textContent||\'\').trim();if((t===txt||t.includes(txt))&&(e.offsetWidth||e.offsetHeight||e.getClientRects().length)){e.click();e.dispatchEvent(new MouseEvent(\'click\',{bubbles:true,cancelable:true}));return \'clicked\'}}return \'not_found\'})(\'Giá Thấp - Cao\')', 'r')
	PL.pause(4000)
	PL.storeEval('(function(txt){var els=Array.from(window.document.querySelectorAll(\'button, div, span, a, label, p\'));for(var i=0;i<els.length;i++){var e=els[i];var t=(e.innerText||e.textContent||\'\').trim();if((t===txt||t.includes(txt))&&(e.offsetWidth||e.offsetHeight||e.getClientRects().length)){e.click();e.dispatchEvent(new MouseEvent(\'click\',{bubbles:true,cancelable:true}));return \'clicked\'}}return \'not_found\'})(\'Phổ biến\')', 'r')
	PL.pause(4000)
	PL.storeEval('Array.prototype.filter.call(window.document.querySelectorAll(\'${cssCard}\'),function(e){return !!(e.offsetWidth||e.offsetHeight||e.getClientRects().length)}).length', 'n1')
	PL.echo('Sau thao tac n1=${n1}')
	PL.storeEval('(Number(storedVars[\'n1\'] || \'${n1}\') > 0) ? \'PASS\' : \'FAIL: Co ket qua\'', 'kq31')
	PL.echo('KIEM TRA Co ket qua: ${kq31}')
	PL.storeEval('(function(){var prices=[];Array.prototype.forEach.call(window.document.querySelectorAll(\'${cssPrice}\'),function(e){if(e.offsetWidth||e.offsetHeight||e.getClientRects().length){var v=parseInt((e.textContent||\'\').replace(/\\D/g,\'\'));if(v)prices.push(v)}});if(prices.length<2) return \'PASS\';var isAscending=true;for(var i=1;i<prices.length;i++){if(prices[i]<prices[i-1]) isAscending=false}return (!isAscending)?\'PASS\':\'FAIL: Thu tu khong con tang dan theo gia\'})()', 'kq32')
	PL.echo('KIEM TRA Thu tu khong con tang dan theo gia: ${kq32}')
} finally {
	PL.endSession()
}
